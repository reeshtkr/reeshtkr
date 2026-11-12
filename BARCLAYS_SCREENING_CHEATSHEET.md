# Barclays Software Engineer Screening Cheat Sheet
**Role:** AVP Software Engineer · Pune · Java/Spring · 8.5 YOE  
**Format:** Few MCQs + Coding (typically HackerRank / HackerEarth)

> Compiled from public candidate reports (2024–2026). Patterns vary by drive.

---

## 1. What to expect

| Pattern | Contents | Time tip |
|---|---|---|
| Classic India OA | ~12–20 MCQs + 1 SQL + 1 coding | Leave 25–35m for coding |
| MCQ-heavy | 20 OOP/output MCQs + 3–5 SQL | Max 2m per MCQ |
| Coding-first (exp) | 2 coding in ~60m (± SQL) | Pass all cases > fancy code |

**Bar:** Easy–Medium coding. Highest MCQ weight = Java output tracing + SQL/OOP.  
Spring/Kafka depth is for **later rounds**, not tonight’s grind.

### Do
- Read input format carefully (parse traps are common)
- Trace Java snippets line-by-line before answering
- SQL: clean JOINs + GROUP BY; check month edge cases
- Ship correct O(n)/O(n log n); polish only if time left

### Don’t
- Chase LeetCode Hard / fancy DP
- Switch windows if proctored
- Skip coding to finish every MCQ
- Over-engineer

---

## 2. MCQ trap checklist (Java / OOP)

| Topic | Quick recall |
|---|---|
| Overload vs override | Override = runtime (same signature); overload = compile-time |
| static methods | Hidden, not overridden |
| `==` vs `equals` | Literals may intern; `new String` is always new |
| finally | Runs after return (unless `System.exit`) |
| Checked vs unchecked | `IOException` checked; `RuntimeException` unchecked |
| HashMap / LinkedHashMap / TreeMap | unordered / insert order / sorted |
| Integer cache | `-128..127` → `==` true; outside → false |
| default methods | Conflict → must override |

### Mini drills
```java
// Q1 → false, true
String s1 = "java";
String s2 = new String("java");
System.out.println(s1 == s2);
System.out.println(s1.equals(s2));

// Q2 → true, false (Integer cache)
Integer a = 100, b = 100, c = 200, d = 200;
System.out.println(a == b);
System.out.println(c == d);

// Q3 → prints B (runtime override). If static → A
class A { void m(){ System.out.print("A"); } }
class B extends A { void m(){ System.out.print("B"); } }
A x = new B(); x.m();
```

**Also know:** HashMap allows 1 null key; Hashtable/ConcurrentHashMap allow none.  
ACID, JOIN types, WHERE vs HAVING, TCP vs UDP, REST idempotency (GET/PUT/DELETE yes; POST no).

---

## 3. Java flash (collections + concurrency)

| Structure | Avg | Notes |
|---|---|---|
| ArrayList | O(1) get | resize ~1.5x |
| HashMap | O(1) avg | treeify at 8 (table ≥ 64) |
| TreeMap | O(log n) | sorted |
| PriorityQueue | O(log n) offer/poll | heap |
| ConcurrentHashMap | O(1) | no nulls |

**HashMap path:** hash → `(n-1)&hash` → list → tree if long → resize at load 0.75.  
**volatile** = visibility, not atomicity. **equals + hashCode** always together.  
Immutable object = final class + final fields + no setters + defensive copies.

---

## 4. Spring / Kafka (skim for screen; deep for later)

**Spring:** `@SpringBootApplication`, constructor injection, `@Bean` vs `@Component`, `@Qualifier`/`@Primary`, `@Transactional` proxy pitfall, Actuator lockdown.

**Kafka one-liners:**
- Order guaranteed **per partition**
- One partition → one consumer in a group
- Process then commit → **at-least-once** → make consumer idempotent
- Crash before commit → redelivery
- Hot partition = bad key distribution
- Sticky assignor reduces rebalance thrash

---

## 5. SQL templates (treat as a coding question)

```sql
-- Month filter
SELECT * FROM orders WHERE MONTH(order_date) = 10;
-- or range: order_date >= '2024-10-01' AND order_date < '2024-11-01'

-- Join + aggregate
SELECT d.name, COUNT(e.id) AS emp_count, AVG(e.salary) AS avg_sal
FROM department d
LEFT JOIN employee e ON e.dept_id = d.id
GROUP BY d.name
HAVING COUNT(e.id) > 5;

-- Top 3 salaries per dept
SELECT * FROM (
  SELECT e.*, DENSE_RANK() OVER (PARTITION BY dept_id ORDER BY salary DESC) rnk
  FROM employee e
) t WHERE rnk <= 3;

-- 2nd highest
SELECT MAX(salary) FROM employee
WHERE salary < (SELECT MAX(salary) FROM employee);

-- Duplicates
SELECT email, COUNT(*) FROM users GROUP BY email HAVING COUNT(*) > 1;
```

**Gotchas:** `IS NULL`; WHERE on right table can turn LEFT → INNER; `COUNT(col)` ignores NULLs.  
ROW_NUMBER = unique; RANK = gaps; DENSE_RANK = no gaps.

---

## 6. Coding archetypes (Barclays recycles shapes)

| Shape | Approach | Practice |
|---|---|---|
| Arrays + HashMap | freq / two-sum | first unique char |
| Sliding window | two pointers + map | longest with K distinct |
| Strings | counts / two pointers | anagrams |
| Greedy + sort | sort ends | buy 1 get K free |
| Intervals | sort by end | activity selection |
| Prefix sum | map prefix→index | equal 0/1 subarray |
| Heap | PriorityQueue | top-K / scheduling |

### Skeletons
```java
// Sliding window
int left = 0; Map<Character,Integer> freq = new HashMap<>(); int best = 0;
for (int right = 0; right < s.length(); right++) {
  freq.merge(s.charAt(right), 1, Integer::sum);
  while (freq.size() > k) {
    char L = s.charAt(left++);
    freq.put(L, freq.get(L) - 1);
    if (freq.get(L) == 0) freq.remove(L);
  }
  best = Math.max(best, right - left + 1);
}

// Equal 0s/1s via prefix (0 → -1)
int sum = 0, best = 0; Map<Integer,Integer> first = new HashMap<>();
first.put(0, -1);
for (int i = 0; i < a.length; i++) {
  sum += (a[i] == 0 ? -1 : 1);
  if (first.containsKey(sum)) best = Math.max(best, i - first.get(sum));
  else first.put(sum, i);
}

// Activity selection
Arrays.sort(intervals, Comparator.comparingInt(x -> x[1]));
int count = 0, lastEnd = Integer.MIN_VALUE;
for (int[] iv : intervals) if (iv[0] >= lastEnd) { count++; lastEnd = iv[1]; }
```

### Platform checklist
1. Parse N then N lines  
2. 0-based vs 1-based  
3. Exact print format / YES-NO case  
4. Empty / single / all-same edges  
5. Use `long` for sums  
6. TLE → HashMap or sort+two pointers  
7. Submit after samples + 2 edges pass

---

## 7. Tonight plan (~3 hours)

| Block | Min | Focus |
|---|---|---|
| Warm-up | 20 | 20 Java output MCQs; log misses |
| SQL | 40 | Joins, aggregation, basic windows |
| Coding | 70 | 4 Easy–Med in HackerRank editor |
| Collections | 20 | HashMap, ==/equals, Integer cache, finally |
| JD skim | 20 | Kafka delivery + Spring DI |
| Sleep | — | Stop 30–45m before bed |

**Morning (20m):** miss log only + rewrite window/prefix from memory + one JOIN/GROUP BY.  
**During test:** scan timers → MCQs ≤90s → coding brute→optimize → last 5m format/off-by-one.

### After you clear screening
**RISES:** Respect, Integrity, Service, Excellence, Stewardship  
**Mindset:** Empower, Challenge, Drive  
AVP stories: coaching, risk/controls ownership, cross-team influence.

---

## Bottom line
Win tomorrow on **Java output accuracy + SQL + one clean Easy–Medium coding**.  
Save Spring/Kafka/system design firepower for technical rounds.
