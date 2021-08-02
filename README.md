![example workflow](https://github.com/ohager/signum-vanity-address-generator/actions/workflows/build-release.yml/badge.svg)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=ohager_signum-vanity-address-generator&metric=security_rating)](https://sonarcloud.io/dashboard?id=ohager_signum-vanity-address-generator)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=ohager_signum-vanity-address-generator&metric=vulnerabilities)](https://sonarcloud.io/dashboard?id=ohager_signum-vanity-address-generator)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=ohager_signum-vanity-address-generator&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=ohager_signum-vanity-address-generator)

# signum-vanity-address-generator

Vanity Address Generator for the Signum Blockchain Platform

Create your personalized Signum Address, like `S-BEER-XYBN-2G34-H98GT`

# How it works

> Prerequisites: [OpenJDK 11](https://openjdk.java.net/install/) installed

Download the jar file from the Github releases and run

`java -jar signum-vanity.jar --help`


```
Usage: signum-vanity [-hVw] [-o=<timeout>] [-p=<position>] -t=<target>
Creates a vanity address for the Signum blockchain platform
  -h, --help                Show this help message and exit.
  -o, --timeout=<timeout>   Timeout to cancel the search after x minutes passed
  -p, --position=<position> The position for the vanity part from 1 to 4
  -t, --target=<target>     The targeted vanity part
  -V, --version             Print version information and exit.
  -w, --words               Creates a BIP39 12 word passphrase
```

| Option | Description  | Required  | Default  |  
|---|---|---|---|
| -p  | The position in the address part where the target shall appear, starting with 1 and at maximum 4. Position 2 would be `S-xxxx-HERE-xxxx-xxxxx`   |  No | 1  | 
| -t  | The target string in the address which can be at maximum 4 characters, or 5 respectively for the last position (4). It must not contain the following chars `I 1 0 O `   |  Yes |   | 
| -w  | Creates a 12 word passphrase based on BIP39 word list | No | false  | 
| -o  | Defines a timeout in minutes.   | No | 30  | 

Example:

The following example _might_ generate the following address: `S-BEER-9U6Y-BS43-PL79A`

```bash 
java -jar signum-vanity.jar -t=BEER 
```

The following example _might_ generate the following address: `S-RT5V-765H-YTU6-5PACE`
and a twelve words passphrase, which stops searching after 15 minutes passed. 

```bash 
java -jar signum-vanity.jar -t=5PACE -p=4 -w -o=15
```

> Note that the generation can take a while (up to some minutes), especially when wanting 4 or 5 chars.
> The last part can only start with letters [2-9A-H]

# Security

The Vanity Address Generator uses a cryptographically secure (`SecureRandom`) randomized passphrase and checks if the resulting address meets the desired address pattern.
Each iteration a new random passphrase is being generated, such there is no predictable pattern for the addresses/passphrases.
The resulting passphrase is either based upon an 58 alphanumeric alphabet and has 80 characters, or creates a [BIP39](https://en.bitcoin.it/wiki/Seed_phrase) phrase.


Powered by 
<img src="./signumj.svg" alt="signumj" height="48" align="middle" />

