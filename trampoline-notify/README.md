# trampoline-notify

trampoline-notify aims to provide multiple ways to notify someone.
For now it contains a wrapper around Spring Mail which allows us to send rich html emails with ease.

The emails are templated using thymeleaf(you can easily implement your own templating implementation).

## How to use trampoline-notify email

First you should configure the mail settings with traditional spring properties.
These can be found [here](../docs/application-prod-example.properties).

Then you may want to configure some defaults such as the sender address(noreply@example.com for example).
We have the following properties:

- `trampoline.notify.email.`: required to be `true` to even make use of this service.
- `trampoline.notify.email.defaults.from`: address from which the emails are sent.
- `trampoline.notify.email.defaults.template`: default (fallback) template to render.
- `trampoline.notify.email.defaults.subject`: default (fallback) subject for emails. 

You can then inject an instance of `EmailService` and craft messages using the builder or by directly invoking the send method.

Check [BlogPostCommentController.java](../example-app/src/main/java/com/lucadev/example/trampoline/web/controller/BlogPostCommentController.java) for an example.