

All five rules live in
`src/test/java/com/example/bookstore/architecture/LayeredArchitectureTest.java`.


The five breakages are on these scratch branches:

| #   | Branch                | Rule broken                             |
| --- | --------------------- | --------------------------------------- |
| 1   | `scratch/arch-rule-1` | rule1_layersOnlyDependDownwards         |
| 2   | `scratch/arch-rule-2` | rule2_serviceHasNoHttpConcerns          |
| 3   | `scratch/arch-rule-3` | rule3_serviceHasNoSqlConcerns           |
| 4   | `scratch/arch-rule-4` | rule4_controllersDoNotTouchRepositories |
| 5   | `scratch/arch-rule-5` | rule5_nomodelTypesInControllers         |

---


