# ANSWERS.md — Structural Tests (Tasks 3 & 5)

All five rules live in
`src/test/java/com/example/bookstore/architecture/LayeredArchitectureTest.java`.
Each rule was broken once on a scratch branch, the exact failure recorded below,
then the branch was reverted.

The five breakages are on these scratch branches:

| #   | Branch                | Rule broken                             |
| --- | --------------------- | --------------------------------------- |
| 1   | `scratch/arch-rule-1` | rule1_layersOnlyDependDownwards         |
| 2   | `scratch/arch-rule-2` | rule2_serviceHasNoHttpConcerns          |
| 3   | `scratch/arch-rule-3` | rule3_serviceHasNoSqlConcerns           |
| 4   | `scratch/arch-rule-4` | rule4_controllersDoNotTouchRepositories |
| 5   | `scratch/arch-rule-5` | rule5_nomodelTypesInControllers         |

---

## Rule 1 — one-directional layering

**Baseline:** PASS.

**Break (`scratch/arch-rule-1`):** Added a field of type `BookRepository` to
`BookController`.

**Verbatim failure message:**
