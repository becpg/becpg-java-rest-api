# Migration guide

## 2.0

### Breaking changes

- Spring Boot 4 / Spring Framework 7 and Jackson 3 (CVE-2026-47890, #37477): integrating applications must run Spring Boot 4 and move their own Jackson code to the `tools.jackson` packages.
- The OAuth2 `password` grant is gone (removed in Spring Security 7): use `client_credentials` for a technical account, or Basic authentication to act as a named user.
