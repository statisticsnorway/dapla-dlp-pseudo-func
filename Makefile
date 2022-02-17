.PHONY: default
default: | help

.PHONY: build-mvn
build-mvn: ## Build the project and install to your local maven repo
ifndef skipTest
	mvn clean install
else
	mvn clean install -Dmaven.test.skip=true
endif

.PHONY: test
test: ## Run tests
	mvn clean test

.PHONY: release-dryrun
release-dryrun: ## Simulate a release in order to detect any issues
	mvn release:prepare release:perform -Darguments="-Dmaven.deploy.skip=true" -DdryRun=true

.PHONY: release
release: ## Release a new version. Update POMs and tag the new version in git.
	mvn release:prepare release:perform -Darguments="-Dmaven.deploy.skip=true -Dmaven.javadoc.skip=true"

.PHONY: help
help:
	@grep -E '^[a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) | awk 'BEGIN {FS = ":.*?## "}; {printf "\033[36m%-30s\033[0m %s\n", $$1, $$2}'
