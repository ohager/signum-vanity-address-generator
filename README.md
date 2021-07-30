# signum-vanity-address-generator

Vanity Address Generator for the Signum Blockchain Platform

Create your personalized Signum Address, like `S-BEER-XYBN-2G34-H98GT`

# How it works

> Prerequisites: [OpenJDK 1.8](https://openjdk.java.net/install/) installed

Download the jar file from the Github releases and run

`java -jar signum-vag.jar --help`


```
Usage: signum-vag [-hV] [-p=<position>] -t=<target>
Creates a vanity address for the Signum blockchain platform
  -h, --help              Show this help message and exit.
  -p, --position=<position>
                          The position for the vanity part from 1 to 4
  -t, --target=<target>   The targeted vanity part
  -V, --version           Print version information and exit.
```

| Option | Description  | Required  | Default  |  
|---|---|---|---|
| -p  | The position in the adress part where the target shall appear, starting with 1 and at maximum 4. Position 2 would be `S-xxxx-HERE-xxxx-xxxxx`   |  No | 1  | 
| -t  | The target string in the address which can be at maximum 4 characters, or 5 respectively for the last position (4). It must not contain the following chars `I 1 0 O `   |  Yes |   | 

Example:

The following example _might_ generate the following address: `S-BEER-9U6Y-BS43-PL79A`

```bash 
java -jar signum-vag.jar -t=BEER 
```

The following example _might_ generate the following address: `S-RT5V-765H-YTU6-METAL`

```bash 
java -jar signum-vag.jar -t=METAL -p=5 
```

> Note that the generation can take a while (up to some minutes), especially when wanting 4 or 5 chars. 

# Security

The Vanity Address Generator uses a cryptographically secure randomized passphrase and checks if the resulting address meets the desired address pattern.
Each iteration a new random passphrase is being generated, such there is no predictable pattern for the addresses/passphrases.
The resulting passphrase is based upon an 58 alphanumeric alphabet and has 80 characters, so should be sufficiently strong.



Powered by 
<img src="./signumj.svg" alt="signumj" height="48" align="middle" />

