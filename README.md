# Dapla Pseudonymization Functions

> Data pseudonymization functions used by Statistics Norway (SSB)

Pseudonymization is a data management and de-identification procedure by which personally identifiable information fields within a data record are replaced by one or more artificial identifiers, or pseudonyms. A single pseudonym for each replaced field or collection of replaced fields makes the data record less identifiable while remaining suitable for data analysis and data processing.

This lib contains functions that can be used to implement data pseudonymization.

It is important to note that pseudonymization is not the same as anonymization. While pseudonymization targets directly identifying elements, the real information might still be identifiable e.g. by using inherent information such as correlation between data elements. Thus, sensitive data that has been pseudonomized using the functions in this library should still be regarded as sensitive.

The library is currently in a "pre-alpha" stage. We're experimenting with the architecture related to how and when psedonymization is being applied in our data management platform. Also, currently there are only a few and simplistic functions in this library. Breaking changes should be expected.

## Installation

Maven coordinates:
```
<dependency>
  <groupId>no.ssb.dapla.dlp.pseudo.func</groupId>
  <artifactId>dapla-dlp-pseudo-func</artifactId>
  <version>0.1.0-SNAPSHOT</version>
</dependency>
```

## Examples

### Pseudo function config

The following exemplifies how a pseudo function config can be described in JSON:
```
[
	{
		"name": "fpe-alphanumeric",
		"impl": "no.ssb.dapla.dlp.pseudo.func.fpe",
		"keyId": "411f2af1-7588-4c7f-95e4-1c15d82ef202",
		"alphabet": "alphanumeric+whitespace"
	},
	{
		"name": "fpe-digits",
		"impl": "no.ssb.dapla.dlp.pseudo.func.fpe",
		"keyId": "411f2af1-7588-4c7f-95e4-1c15d82ef202",
		"alphabet": "digits"
	},
	{	
		"name": "fpe-custom",
		"impl": "no.ssb.dapla.dlp.pseudo.func.FpeFunc",
		"keyId": "blah",
		"alphabet": "abcdefghij123_ "
	}
] 
```

The `name` and `impl` properties are mandatory for all functions.
Other properties are dependent on the implentation underneath. In the above,
the `FpeFunc` requires additional properties `keyId` and `alphabet`. Notice that
the `keyId` is just a reference to a key that can be looked up via a standalone
service.

Pseudonymized datasets will (via metadata) reference pseudo functions by name
to describe how certain columns in a dataset has been pseudonymized.

### Initialize function registry
```java
String configJson = ...
PseudoFuncRegistry registry = new PseudoFuncRegistry();
registry.init(configJson)
```

### Invoke pseudonymization function
```java
PseudoFunc func = registry.get("fpe-alphanumeric");
PseudoFuncInput input = PseudoFuncInput.of("Ken sent me");
PseudoFuncOutput output = func.apply(input); // '2y RazFwxQM'
```

### Restore to original value ("depseudonymize")
```java
PseudoFunc func = registry.get("fpe-alphanumeric");
PseudoFuncInput input = PseudoFuncInput.of("2y RazFwxQM");
PseudoFuncOutput output = func.restore(input); // 'Ken sent me'
```

### Explicit instantiation of PseudoFunc
```java
PseudoFuncConfig config = new PseudoFuncConfig(Map.of(
    PseudoFuncConfig.Param.FUNC_NAME, "myfunc-1",
    PseudoFuncConfig.Param.FUNC_IMPL, MyFunc.class.getName(),
    // ... paramName = value
));
PseudoFunc func = PseudoFuncFactory.create(config);
```

For more usage examples, have a look at the [tests](https://github.com/statisticsnorway/dapla-dlp-pseudo-func/tree/master/tests).

## Development

Run `make help` to see common development commands.

```
build-mvn                      Build the project and install to you local maven repo
test                           Run tests
release-dryrun                 Simulate a release in order to detect any issues
release                        Release a new version. Update POMs and tag the new version in git.
```

## Contributing
1. Fork it (https://github.com/statisticsnorway/dapla-dlp-pseudo-func/fork)
2. Create your feature branch (`git checkout -b feature/foo-bar`)
3. Commit your changes (`git commit -am 'Add some foo bar'`)
4. Push to the branch (`git push origin feature/foo-bar`)
5. Create a new Pull Request