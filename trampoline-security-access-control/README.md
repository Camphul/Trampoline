# trampoline-security-access-control

Access control mechanism module.

## Mechanism

1. User requests resource
2. Resource secured through hasPermission
3. Preprocess some data coming from the hasPermission call
4. Evaluate access result through the use of a decision manager and multiple access evaluators
