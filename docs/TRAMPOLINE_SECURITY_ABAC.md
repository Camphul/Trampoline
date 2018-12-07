# trampoline-security-abac docs

Attribute based access control enabled row level security for Spring using SPeL expression that are persisted or loaded from Json.

## PolicyRule
The actual spel expressions
## PolicyDefinition
Collection of the rules.
## PolicyEnforcement
Checks the rules against the subject/resource
## AbacPermissionEvaluator
Binds to spring permission evaluator to policy enforcement
## SecurityAccessContext
Context in which the spel expressions are ran.