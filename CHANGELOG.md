## [ 1.1.5 ] - mar. 28 janv. 2025 10:58:17 CET

* [Feature] Fixed single assoc issue in getAssociations + added userName and authorityName to name init
* [Setup] Update libs
* Allow to customize ConnectionProvider
* [Setup] Update documentation
* Add pagination for list

## [ 1.1.6 ] - jeu. 28 août 2025 14:07:32 CEST

* [Release] prepare release 1.1.6
* Launch build
* Fix list all pages of entityAPI
* Set connectionProvider protected
* Add logRequest filter
* Fix #27797 - [Feature] Add overload methods for advanced search API
* Fix #27797 - [Feature] Add advanced search in remote list API
* [Setup] Update documentation
* [skip ci][Release]: Update CHANGELOG.md for 1.1.5 release
* [skip ci] Prepare for next development iteration

## [ 1.1.7 ] - lun. 29 sept. 2025 11:27:49 CEST

* [Release] prepare release 1.1.7
* [Setup] Fix unit test
* [Setup] Update libs
* [Bug] Fix unit test
* Merge branch 'main' of https://github.com/becpg/becpg-java-rest-api
* [Bug] Do not compress param if empty attributes list
* [skip ci][Release]: Update CHANGELOG.md for 1.1.6 release
* [skip ci] Prepare for next development iteration
* [Setup] Update library

## [ 1.1.8 ] - jeu. 22 janv. 2026 17:02:35 CET

* [Release] prepare release 1.1.8
* [Feature] - Add HTTP/1.1 and TLSv1.2 forcing options and document them
* [Setup] Update dependencies
* Add DictionaryHelper
* Fix allergenListAllergen assoc name
* [Feature] Improve BecpgAPIModel
* [Feature] Add BecpgAPIModel and helpers for setAssociations
* [skip ci][Release]: Update CHANGELOG.md for 1.1.7 release
* [skip ci] Prepare for next development iteration

## [ 1.1.9 ] - jeu. 26 févr. 2026 14:33:22 CET

* [Release] prepare release 1.1.9
* [Setup] Prepare for release
* Merge branch 'main' of https://github.com/becpg/becpg-java-rest-api.git into main
* Add RemoteEntityHelper class
* [skip ci][Release]: Update CHANGELOG.md for 1.1.8 release
* [skip ci] Prepare for next development iteration

## [ 1.1.10 ] - jeu. 05 mars 2026 17:21:01 CET

* [Release] prepare release 1.1.10
* [Sonar] Fix sonar issue
* Fix #32325 - [Feature] Add session management
* [Bug] Deprecate DictionnaryHelper
* Fix #32325 - [Feature] Add session management
* [Setup] Fix build
* Fix #32325 - [Feature] Add session management
* [skip ci][Release]: Update CHANGELOG.md for 1.1.9 release
* [skip ci] Prepare for next development iteration

## [ 1.1.11 ] - ven. 06 mars 2026 17:57:28 CET

* [Release] prepare release 1.1.11
* [Setup] Prepare release
* [Bug] Disable basic auth when oauth is on
* [skip ci][Release]: Update CHANGELOG.md for 1.1.10 release
* [skip ci] Prepare for next development iteration

## [ 1.1.12 ] - lun. 09 mars 2026 16:46:41 CET

* [Release] prepare release 1.1.12
* [Feature] Add unit test on datalist
* Fix #17675 - [Bug] Fix alf ticket encoding
* [skip ci][Release]: Update CHANGELOG.md for 1.1.11 release
* [skip ci] Prepare for next development iteration

## [ 1.1.13 ] - mar. 21 avril 2026 10:37:43 CEST

* [Release] prepare release 1.1.13
* Update README
* Add bcpg:allergenListQtyPerc field constant
* [Feature] Generalize Entity API parameters and improve null safety
* [skip ci][Release]: Update CHANGELOG.md for 1.1.12 release
* [skip ci] Prepare for next development iteration

## [ 1.1.14 ] - mar. 02 juin 2026 17:17:18 CEST

* [Release] prepare release 1.1.14
* [Feature] Add system params to list method
* Merge remote-tracking branch 'origin/feature/34448' into main
* [Feature] Add system params to list method
* [Documentation] Update readme
* Fix #34448 - [Feature] Implement secure batch start, ack, and end endpoints in ChannelAPIClient
* [skip ci][Release]: Update CHANGELOG.md for 1.1.13 release
* [skip ci] Prepare for next development iteration

## [ 1.1.15 ] - jeu. 04 juin 2026 13:59:30 CEST

* [Release] prepare release 1.1.15
* [Setup] Preoare release
* [Feature] Add JSON Schema validation constraints (maxLength, minLength, minimum, maximum) to RemoteEntitySchema
* [Feature] Add bcpg:toxList constants to BeCPGAPIModel
* [Feature] Add cm:firstName, cm:lastName, and cm:email constants to BeCPGAPIModel
* [Cleanup] Deprecate BeCPGAPIModel.TYPE_PROJECT in favour of ProjectAPIModel
* [Feature] Add ProjectAPIModel constants for pjt: model fields, deprecate BeCPGAPIModel pjt: constants
* [skip ci][Release]: Update CHANGELOG.md for 1.1.14 release
* [skip ci] Prepare for next development iteration

## [ 1.1.16 ] - ven. 26 juin 2026 15:30:36 CEST

* [Release] prepare release 1.1.16
* [Bug] Fix EntityApi/ChannelApi tests broken by Basic-auth ticket login
* [Feature] Add configurable HTTP connect/response timeouts
* [Bug] Fix OAuth2 auto-config conditional activation
* Reduce Keycloak load: cache out-of-session alf_ticket and enable refresh tokens
* [skip ci][Release]: Update CHANGELOG.md for 1.1.15 release
* [skip ci] Prepare for next development iteration

## [ 1.1.17 ] - mer. 15 juil 2026 11:48:20 CEST

* [Release] prepare release 1.1.17
* Add new ingList fiels def
* [skip ci][Release]: Update CHANGELOG.md for 1.1.16 release
* [Release] prepare release 1.1.16
* [skip ci] Prepare for next development iteration
* [Release] prepare release 1.1.16
* [Bug] Fix EntityApi/ChannelApi tests broken by Basic-auth ticket login
* [Bug] Fix EntityApi/ChannelApi tests broken by Basic-auth ticket login
* [Feature] Add configurable HTTP connect/response timeouts
* [Feature] Add configurable HTTP connect/response timeouts
* [Bug] Fix OAuth2 auto-config conditional activation
* [Bug] Fix OAuth2 auto-config conditional activation
* Reduce Keycloak load: cache out-of-session alf_ticket and enable refresh tokens
* Reduce Keycloak load: cache out-of-session alf_ticket and enable refresh tokens

## [ 1.1.18 ] - ven. 21 août 2026 11:00:37 CEST

* [Release] prepare release 1.1.18
* [Setup] Update documentation for 1.1.18
* Fix #36148 - [Bug] Read a d:date published as a calendar day, keeping the legacy instant form
* InProgress #35457 - [Bug] Provide the reactive OAuth2 client registration repository in servlet applications
* [Setup] Declare the sonar-maven-plugin so the CI sonar step resolves
* [Setup] Track the Maven wrapper files so mvnw works on a fresh clone
* Fix #35955 - [Security] Bump Spring Boot to 3.5.16 for Spring Security 6.5.11 (CVE-2026-22732)
* [skip ci][Release]: Update CHANGELOG.md for 1.1.17 release
* [skip ci] Prepare for next development iteration

## [ 1.1.19 ] - jeu. 27 août 2026 17:05:48 CEST

* [Release] prepare release 1.1.19
* Fix #36271 - [Setup] Make @EnableAuthConfiguration provide the remote client factory it now needs
* Fix #36271 - [Bug] Apply the wiretap option to the client instead of discarding it
* Fix #36271 - [Bug] Cover the SDK auto-configuration wiring with a test
* [Setup] Update documentation for 1.1.19
* Fix #36271 - [Cleanup] Drop the password grant from the SDK documentation
* Fix #36271 - [Bug] Apply the remote client settings to the OAuth2 token client as well
* Fix #36271 - [Bug] Apply remote.ssl.trustAll and the remote client settings to the Alfresco login client
* [skip ci][Release]: Update CHANGELOG.md for 1.1.18 release
* [skip ci] Prepare for next development iteration

## [ 1.1.20 ] - ven. 28 août 2026 13:49:06 CEST

* [Release] prepare release 1.1.20
* Fix #36271 - [Bug] Keep the HTTP status when the remote error body is not JSON
* [Security] Keep the sample application.properties free of real credentials
* [skip ci][Release]: Update CHANGELOG.md for 1.1.19 release
* [skip ci] Prepare for next development iteration

## [ 1.1.21 ] - lun. 14 sept 2026 16:30:52 CEST

* [Release] prepare release 1.1.21
* Merge branch 'main' of https://github.com/becpg/becpg-java-rest-api.git into main
* Fix #36516 - [Feature] Expose the types an association accepts from its assoc:targetTypes schema
* [skip ci][Release]: Update CHANGELOG.md for 1.1.20 release
* [skip ci] Prepare for next development iteration

## [ 1.1.22 ] - lun. 05 oct. 2026 16:11:45 CEST

* [Release] prepare release 1.1.22
* Fix #37236 - [Bug] Remove unnecessary '/' in getOrCreateSharedUrl
* [skip ci][Release]: Update CHANGELOG.md for 1.1.21 release
* [skip ci] Prepare for next development iteration

## [ 2.0.0 ] - jeu. 29 oct 2026 14:50:13 CET

* [Release] prepare release 2.0.0
* InProgress #37477 - [Security] - Move to Spring Boot 4.0.9 / Spring Framework 7.0.10 and Jackson 3 for CVE-2026-47890, version 2.0.0
* [Setup] - Publish the full LGPL v3 text as LICENSE and keep the beCPG copyright notice in NOTICE
* [skip ci][Release]: Update CHANGELOG.md for 1.1.22 release
* [skip ci] Prepare for next development iteration

## [ 2.0.1 ] - lun. 02 nov 2026 10:53:32 CET

* [Release] prepare release 2.0.1
* [Cleanup] - Update dependencies of week 44 in becpg-java-rest-api
* [Setup] - Give the project its technical name and a description for SonarQube
* [skip ci][Release]: Update CHANGELOG.md for 2.0.0 release
* [skip ci] Prepare for next development iteration

## [ 2.0.2 ] - lun. 02 nov 2026 14:33:20 CET

* [Release] prepare release 2.0.2
* [Setup] Update README and MIGRATION for 2.0.2
* [Bug] - Find the previous tag with git describe in release.sh so x.y.0 releases get their changelog
* [skip ci][Release]: Update CHANGELOG.md for 2.0.1 release
* [skip ci] Prepare for next development iteration

## [ 2.0.3 ] - jeu. 05 nov 2026 09:55:43 CET

* [Release] prepare release 2.0.3
* [Merge] Merge feature/#38128 into main
* [Setup] Add AGENTS.md with the README update at each release
* Fix #38128 - [Feature] Read definedBy in RemoteEntitySchema
* [Setup] Update README.md
* [skip ci][Release]: Update CHANGELOG.md for 2.0.2 release
* [skip ci] Prepare for next development iteration

## [ 2.0.4 ] - mar. 10 nov 2026 09:56:08 CET

* [Release] prepare release 2.0.4
* [Bug] Keep the remote JSON unchanged through the client model
* [skip ci][Release]: Update CHANGELOG.md for 2.0.3 release
* [skip ci] Prepare for next development iteration

## [ 2.1.0 ] - ven. 13 nov 2026 13:27:32 CET

* [Release] prepare release 2.1.0
* [Setup] Update README and MIGRATION for 2.1.0
* Fix #38440 - [Feature] Report a missing merge target through the Mono and require remote API 3.0
* Fix #38440 - [Feature] Add formulate, branch, merge and delete methods to EntityAPI
* [Cleanup] Cover the equality of RemoteEntitySchema on its new keywords
* [Setup] Import the JaCoCo coverage into SonarQube in becpg-java-rest-api
* [skip ci][Release]: Update CHANGELOG.md for 2.0.4 release
* [skip ci] Prepare for next development iteration

