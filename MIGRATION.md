# Migration guide

## 2.1.2

### Breaking changes

- `EntityAPI` gets `formulate(String, String)`, `copy` and `move` and their reactive variants, and `ContentAPI` gets `uploadDocument` and
  `uploadDocumentVersion` (#38476): an application that implements these interfaces itself, rather than injecting or mocking them, must
  implement the new methods. Nothing changes for the applications that only call them.
- `copy` / `move` take the new record `RemoteNodeRelocation`, the uploads `RemoteDocumentUpload` / `RemoteVersionUpload`, and they answer
  `RemoteNodeActionResult` / `RemoteUploadResult`.

### Notable changes

- Copy, move and upload call the Share webscripts `/alfresco/service/slingshot/doclib/action/{copy-to,move-to}` and
  `/alfresco/service/api/upload`, outside `becpg/remote`, with the same authentication as the other calls. The package-private
  `DoclibActionClient` is registered by the auto-configuration: an application that lists the SDK beans itself must add it.
- `AuditAPI` reads the beCPG statistics webscript (#37279).

## 2.1

### Breaking changes

- `EntityAPI` gets `formulate`, `branch`, `merge` and `delete` and their reactive variants (#38440): an application that implements
  `EntityAPI` itself, rather than injecting it or mocking it, must implement them. Nothing changes for the applications that only call it.

## 2.0

### Breaking changes

- Spring Boot 4 / Spring Framework 7 and Jackson 3 (CVE-2026-47890, #37477): integrating applications must run Spring Boot 4 and move their own Jackson code to the `tools.jackson` packages.
- The OAuth2 `password` grant is gone (removed in Spring Security 7): use `client_credentials` for a technical account, or Basic authentication to act as a named user.
