
## Notes

- [Assignment 10 definition](https://github.com/datsoftlyngby/soft2019spring-test/blob/master/Assignments/10%20CI%20CD%20Assignment.pdf)
- Running the selenium tests more than without reloading the web app will fail. This is because the test "opens an account", and it cannot create multiple accounts with the same name.
- [.travis.yml](.travis.yml) file contains the Travis pipeline definition


## The pipeline

[Travis](https://travis-ci.org) is used for the CI/CD pipeline. The whole manifest of instructions for can be found in the [.travis.yml](.travis.yml) file. Travis will build docker containers for the tests and the web app, then it will run all of them and finally deploy the app to [Heroku](heroku.com), [here](https://cphb-assignment10.herokuapp.com). The builds themselves are triggered on each commit. If any part of the `script` phase of the pipeline exits with a non-zero code, the build will be considered a failed one and the `deploy` phase will not be executed. Thus only code that passes all tests gets deployed.



Image 1 - Travis build history
![travis build history](screenshots/travis_history.png)

**See end of this file for example build output from a successful build.**


## Building the containers

```
docker build -t cphb/assignment10-app -f webapp.Dockerfile .
docker build -t cphb/assignment10-selenium -f selenium.Dockerfile  .
```


## Running the tests inside a container

If both the app and the selenium tests are being run in containers, they need to be in the same network. This can be achieved using the hosts network(passing `--network host` to each container) or creating a network with:
```
docker network create cphb-network
```

**The following commands assume that your current directory contains the project source code.**

Run the web app container:
```
docker run --rm -v $(pwd):/app -w /app -d --network cphb-network --name assignment10-app cphb/assignment10-app
```
Run the selenium test container:
```
docker run --rm -v $(pwd):/app -w /app -d --network cphb-network --env "APP_URL=http://app.test:8080" --link "assignment10-app:app.test" --name assignment10-selenium cphb/assignment10-selenium
```

### Using a test server for selenium tests

The selenium tests can be easily adjusted to use a different url for the website. 



## Running the tests separately

There are three suites within the [suites folder](src/test/java/cphb/suites). You can run either one of them through your IDE to run the coressponding tests. 

You can also run the tests using maven properties. The following commands are equivalent to running each test suite:
```
mvn -Dtest=cphb.AccountTest,cphb.CreditCardTest surefire:test
mvn -Dtest=cphb.AccountContrllerTest surefire:test
mvn -Dtest=cphb.OpenAccountTest surefire:test
```

## The web app

The project itself is a Java web application using Spring MVC. It is sort of a bank application where customers can open an account, login and withthdraw/deposit money, this includes multiple pages with forms. Nothing fancy but it provides stuff to test.. You can also view the application live at its [heroku site](https://cphb-assignment10.herokuapp.com/)

### Example build output


> travis_fold:start:worker_info
> composer --version[0m
> Composer version 1.5.2 2017-09-11 16:59:25
> [34m[1mPre-installed Ruby versions[0m
> ruby-2.2.7
> ruby-2.3.4
> ruby-2.4.1
> travis_fold:end:system_info
> [0K
> travis_fold:start:services
> [0Ktravis_time:start:0964eafc
> [0K$ sudo service docker start
> start: Job is already running: docker
> travis_time:end:0964eafc:start=1555355196381220642,finish=1555355196396917471,duration=15696829
> [0Ktravis_fold:end:services
> [0K
> travis_fold:start:git.checkout
> [0Ktravis_time:start:00784858
> [0K$ git clone --depth=50 --branch=master https://github.com/cphjs/soft2019-test_assignment_10.git cphjs/soft2019-test_assignment_10
> Cloning into 'cphjs/soft2019-test_assignment_10'...
> remote: Enumerating objects: 129, done.[K
> remote: Counting objects:   0% (1/129)   [K
> remote: Counting objects: 100% (129/129), done.[K
> remote: Compressing objects:   1% (1/82)   [K
> remote: Compressing objects: 100% (82/82), done.[K
> Receiving objects:   0% (1/129)   
> Resolving deltas: 100% (52/52), done.
> travis_time:end:00784858:start=1555355199402925103,finish=1555355200193732806,duration=790807703
> [0K$ cd cphjs/soft2019-test_assignment_10
> $ git checkout -qf e80e7f4be5d0bfef3cc5bdf091d1475ae21a848e
> travis_fold:end:git.checkout
> [0K
> travis_fold:start:cache.1
> [0KSetting up build cache
> $ export CASHER_DIR=${TRAVIS_HOME}/.casher
> travis_time:start:16f3afa6
> [0K$ Installing caching utilities
> travis_time:end:16f3afa6:start=1555355204618343580,finish=1555355204860366759,duration=242023179
> [0Ktravis_time:start:05f99db0
> [0Ktravis_time:end:05f99db0:start=1555355204865796932,finish=1555355204868832844,duration=3035912
> [0Ktravis_time:start:15c75450
> [0K[32;1mattempting to download cache archive[0m
> [32;1mfetching master/cache-linux-trusty-e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855.tgz[0m
> [32;1mfound cache[0m
> travis_time:end:15c75450:start=1555355204872729601,finish=1555355206446924217,duration=1574194616
> [0Ktravis_time:start:1ca608a8
> [0Ktravis_time:end:1ca608a8:start=1555355206451447280,finish=1555355206454696406,duration=3249126
> [0Ktravis_time:start:148a3726
> [0K[32;1madding /home/travis/.m2 to cache[0m
> travis_time:end:148a3726:start=1555355206458893516,finish=1555355208683758316,duration=2224864800
> [0Ktravis_fold:end:cache.1
> [0K
> $ java -Xmx32m -version
> java version "1.8.0_151"
> Java(TM) SE Runtime Environment (build 1.8.0_151-b12)
> Java HotSpot(TM) 64-Bit Server VM (build 25.151-b12, mixed mode)
> $ javac -J-Xmx32m -version
> javac 1.8.0_151
> travis_fold:start:before_install.1
> [0Ktravis_time:start:049dd3a8
> [0K$ docker build -t cphb/assignment10-app -f webapp.Dockerfile .
> Sending build context to Docker daemon  557.1kB
> Sending build context to Docker daemon  11.77MB
> 
> 
> Step 1/3 : FROM maven:3.6.0-jdk-8-alpine
> 3.6.0-jdk-8-alpine: Pulling from library/maven
> fc5b684a6b62: Pull complete 
> [1BDigest: sha256:c6ce6f9172294187f3cdca3d87030db0ebe97fb6c0b57da7ed20adfca2608605
> Status: Downloaded newer image for maven:3.6.0-jdk-8-alpine
>  ---> 28939accad54
> Step 2/3 : EXPOSE 8080
>  ---> Running in bedf05f3dcc4
>  ---> b26d161be2ca
> Removing intermediate container bedf05f3dcc4
> Step 3/3 : CMD mvn -DskipTests=true package && java -jar target/dependency/webapp-runner.jar target/*.war
>  ---> Running in ab0cbfd5aa2a
>  ---> da5d3afdb183
> Removing intermediate container ab0cbfd5aa2a
> Successfully built da5d3afdb183
> Successfully tagged cphb/assignment10-app:latest
> travis_time:end:049dd3a8:start=1555355209406391949,finish=1555355215651594182,duration=6245202233
> [0Ktravis_fold:end:before_install.1
> [0Ktravis_fold:start:before_install.2
> [0Ktravis_time:start:108b99bf
> [0K$ docker build -t cphb/assignment10-selenium -f selenium.Dockerfile .
> Sending build context to Docker daemon  557.1kB
> Sending build context to Docker daemon  11.77MB
> 
> 
> Step 1/10 : FROM maven:3.6.0-jdk-8
> 3.6.0-jdk-8: Pulling from library/maven
> 4d700f0944e4: Pull complete 
> [1BDigest: sha256:0379aeb11eb1b36978c9b1724f0903570af43ce671ede6f2a12db75407ebab37
> Status: Downloaded newer image for maven:3.6.0-jdk-8
>  ---> 938cf03ad8e9
> Step 2/10 : ENV GECKODRIVER_VERSION "v0.22.0"
>  ---> Running in 9ac158b434af
>  ---> e46dff3fa3f5
> Removing intermediate container 9ac158b434af
> Step 3/10 : ENV GECKODRIVER_PLATFORM "linux32"
>  ---> Running in 228f22ccaee8
>  ---> 5d4421a9d3ce
> Removing intermediate container 228f22ccaee8
> Step 4/10 : RUN apt-get update     && apt-get install libdbus-glib-1-2 -y
>  ---> Running in e52404eece7b
> Get:1 http://security.debian.org/debian-security stretch/updates InRelease [94.3 kB]
> Ign:2 http://deb.debian.org/debian stretch InRelease
> Get:3 http://deb.debian.org/debian stretch-updates InRelease [91.0 kB]
> Get:4 http://deb.debian.org/debian stretch Release [118 kB]
> Get:5 http://deb.debian.org/debian stretch Release.gpg [2434 B]
> Get:6 http://security.debian.org/debian-security stretch/updates/main amd64 Packages [485 kB]
> Get:7 http://deb.debian.org/debian stretch-updates/main amd64 Packages [11.1 kB]
> Get:8 http://deb.debian.org/debian stretch/main amd64 Packages [7084 kB]
> Fetched 7885 kB in 1s (4231 kB/s)
> Reading package lists...
> Reading package lists...
> Building dependency tree...
> Reading state information...
> The following NEW packages will be installed:
>   libdbus-glib-1-2
> 0 upgraded, 1 newly installed, 0 to remove and 5 not upgraded.
> Need to get 206 kB of archives.
> After this operation, 343 kB of additional disk space will be used.
> Get:1 http://deb.debian.org/debian stretch/main amd64 libdbus-glib-1-2 amd64 0.108-2 [206 kB]
> [91mdebconf: delaying package configuration, since apt-utils is not installed
> [0mFetched 206 kB in 0s (3601 kB/s)
> Selecting previously unselected package libdbus-glib-1-2:amd64.
> 
> (Reading database ... 
> (Reading database ... 5%
> (Reading database ... 10%
> (Reading database ... 15%
> (Reading database ... 20%
> (Reading database ... 25%
> (Reading database ... 30%
> (Reading database ... 35%
> (Reading database ... 40%
> (Reading database ... 45%
> (Reading database ... 50%
> (Reading database ... 55%
> (Reading database ... 60%
> (Reading database ... 65%
> (Reading database ... 70%
> (Reading database ... 75%
> (Reading database ... 80%
> (Reading database ... 85%
> (Reading database ... 90%
> (Reading database ... 95%
> (Reading database ... 100%
> (Reading database ... 21510 files and directories currently installed.)
> 
> Preparing to unpack .../libdbus-glib-1-2_0.108-2_amd64.deb ...
> 
> Unpacking libdbus-glib-1-2:amd64 (0.108-2) ...
> 
> Setting up libdbus-glib-1-2:amd64 (0.108-2) ...
> 
> Processing triggers for libc-bin (2.24-11+deb9u4) ...
> 
>  ---> a17993c7d320
> Removing intermediate container e52404eece7b
> Step 5/10 : RUN apt-get install netcat dnsutils -y
>  ---> Running in 223b2e79e692
> Reading package lists...
> Building dependency tree...
> Reading state information...
> The following additional packages will be installed:
>   bind9-host geoip-database libbind9-140 libdns162 libgeoip1 libisc160
>   libisccc140 libisccfg140 liblwres141 netcat-traditional
> Suggested packages:
>   rblcheck geoip-bin
> The following NEW packages will be installed:
>   bind9-host dnsutils geoip-database libbind9-140 libdns162 libgeoip1
>   libisc160 libisccc140 libisccfg140 liblwres141 netcat netcat-traditional
> 0 upgraded, 12 newly installed, 0 to remove and 5 not upgraded.
> Need to get 5108 kB of archives.
> After this operation, 14.9 MB of additional disk space will be used.
> Get:1 http://deb.debian.org/debian stretch/main amd64 libgeoip1 amd64 1.6.9-4 [90.5 kB]
> Get:2 http://deb.debian.org/debian stretch/main amd64 libisc160 amd64 1:9.10.3.dfsg.P4-12.3+deb9u4 [398 kB]
> Get:3 http://deb.debian.org/debian stretch/main amd64 libdns162 amd64 1:9.10.3.dfsg.P4-12.3+deb9u4 [1077 kB]
> Get:4 http://deb.debian.org/debian stretch/main amd64 libisccc140 amd64 1:9.10.3.dfsg.P4-12.3+deb9u4 [198 kB]
> Get:5 http://deb.debian.org/debian stretch/main amd64 libisccfg140 amd64 1:9.10.3.dfsg.P4-12.3+deb9u4 [223 kB]
> Get:6 http://deb.debian.org/debian stretch/main amd64 libbind9-140 amd64 1:9.10.3.dfsg.P4-12.3+deb9u4 [206 kB]
> Get:7 http://deb.debian.org/debian stretch/main amd64 liblwres141 amd64 1:9.10.3.dfsg.P4-12.3+deb9u4 [214 kB]
> Get:8 http://deb.debian.org/debian stretch/main amd64 bind9-host amd64 1:9.10.3.dfsg.P4-12.3+deb9u4 [231 kB]
> Get:9 http://deb.debian.org/debian stretch/main amd64 netcat-traditional amd64 1.10-41+b1 [67.0 kB]
> Get:10 http://deb.debian.org/debian stretch/main amd64 dnsutils amd64 1:9.10.3.dfsg.P4-12.3+deb9u4 [283 kB]
> Get:11 http://deb.debian.org/debian stretch/main amd64 geoip-database all 20170512-1 [2112 kB]
> Get:12 http://deb.debian.org/debian stretch/main amd64 netcat all 1.10-41 [8962 B]
> [91mdebconf: delaying package configuration, since apt-utils is not installed
> [0mFetched 5108 kB in 0s (35.1 MB/s)
> Selecting previously unselected package libgeoip1:amd64.
> 
> (Reading database ... 
> (Reading database ... 5%
> (Reading database ... 10%
> (Reading database ... 15%
> (Reading database ... 20%
> (Reading database ... 25%
> (Reading database ... 30%
> (Reading database ... 35%
> (Reading database ... 40%
> (Reading database ... 45%
> (Reading database ... 50%
> (Reading database ... 55%
> (Reading database ... 60%
> (Reading database ... 65%
> (Reading database ... 70%
> (Reading database ... 75%
> (Reading database ... 80%
> (Reading database ... 85%
> (Reading database ... 90%
> (Reading database ... 95%
> (Reading database ... 100%
> (Reading database ... 21520 files and directories currently installed.)
> 
> Preparing to unpack .../00-libgeoip1_1.6.9-4_amd64.deb ...
> 
> Unpacking libgeoip1:amd64 (1.6.9-4) ...
> 
> Selecting previously unselected package libisc160:amd64.
> 
> Preparing to unpack .../01-libisc160_1%3a9.10.3.dfsg.P4-12.3+deb9u4_amd64.deb ...
> 
> Unpacking libisc160:amd64 (1:9.10.3.dfsg.P4-12.3+deb9u4) ...
> 
> Selecting previously unselected package libdns162:amd64.
> 
> Preparing to unpack .../02-libdns162_1%3a9.10.3.dfsg.P4-12.3+deb9u4_amd64.deb ...
> 
> Unpacking libdns162:amd64 (1:9.10.3.dfsg.P4-12.3+deb9u4) ...
> 
> Selecting previously unselected package libisccc140:amd64.
> 
> Preparing to unpack .../03-libisccc140_1%3a9.10.3.dfsg.P4-12.3+deb9u4_amd64.deb ...
> 
> Unpacking libisccc140:amd64 (1:9.10.3.dfsg.P4-12.3+deb9u4) ...
> 
> Selecting previously unselected package libisccfg140:amd64.
> 
> Preparing to unpack .../04-libisccfg140_1%3a9.10.3.dfsg.P4-12.3+deb9u4_amd64.deb ...
> 
> Unpacking libisccfg140:amd64 (1:9.10.3.dfsg.P4-12.3+deb9u4) ...
> 
> Selecting previously unselected package libbind9-140:amd64.
> 
> Preparing to unpack .../05-libbind9-140_1%3a9.10.3.dfsg.P4-12.3+deb9u4_amd64.deb ...
> 
> Unpacking libbind9-140:amd64 (1:9.10.3.dfsg.P4-12.3+deb9u4) ...
> 
> Selecting previously unselected package liblwres141:amd64.
> 
> Preparing to unpack .../06-liblwres141_1%3a9.10.3.dfsg.P4-12.3+deb9u4_amd64.deb ...
> 
> Unpacking liblwres141:amd64 (1:9.10.3.dfsg.P4-12.3+deb9u4) ...
> 
> Selecting previously unselected package bind9-host.
> 
> Preparing to unpack .../07-bind9-host_1%3a9.10.3.dfsg.P4-12.3+deb9u4_amd64.deb ...
> 
> Unpacking bind9-host (1:9.10.3.dfsg.P4-12.3+deb9u4) ...
> 
> Selecting previously unselected package netcat-traditional.
> 
> Preparing to unpack .../08-netcat-traditional_1.10-41+b1_amd64.deb ...
> 
> Unpacking netcat-traditional (1.10-41+b1) ...
> 
> Selecting previously unselected package dnsutils.
> 
> Preparing to unpack .../09-dnsutils_1%3a9.10.3.dfsg.P4-12.3+deb9u4_amd64.deb ...
> 
> Unpacking dnsutils (1:9.10.3.dfsg.P4-12.3+deb9u4) ...
> 
> Selecting previously unselected package geoip-database.
> 
> Preparing to unpack .../10-geoip-database_20170512-1_all.deb ...
> 
> Unpacking geoip-database (20170512-1) ...
> 
> Selecting previously unselected package netcat.
> 
> Preparing to unpack .../11-netcat_1.10-41_all.deb ...
> 
> Unpacking netcat (1.10-41) ...
> 
> Setting up geoip-database (20170512-1) ...
> 
> Setting up netcat-traditional (1.10-41+b1) ...
> 
> update-alternatives: using /bin/nc.traditional to provide /bin/nc (nc) in auto mode
> 
> Setting up libgeoip1:amd64 (1.6.9-4) ...
> 
> Processing triggers for libc-bin (2.24-11+deb9u4) ...
> 
> Setting up liblwres141:amd64 (1:9.10.3.dfsg.P4-12.3+deb9u4) ...
> 
> Setting up netcat (1.10-41) ...
> 
> Setting up libisc160:amd64 (1:9.10.3.dfsg.P4-12.3+deb9u4) ...
> 
> Setting up libisccc140:amd64 (1:9.10.3.dfsg.P4-12.3+deb9u4) ...
> 
> Setting up libdns162:amd64 (1:9.10.3.dfsg.P4-12.3+deb9u4) ...
> 
> Setting up libisccfg140:amd64 (1:9.10.3.dfsg.P4-12.3+deb9u4) ...
> 
> Setting up libbind9-140:amd64 (1:9.10.3.dfsg.P4-12.3+deb9u4) ...
> 
> Setting up bind9-host (1:9.10.3.dfsg.P4-12.3+deb9u4) ...
> 
> Setting up dnsutils (1:9.10.3.dfsg.P4-12.3+deb9u4) ...
> 
> Processing triggers for libc-bin (2.24-11+deb9u4) ...
> 
>  ---> 4e38c3e6c09e
> Removing intermediate container 223b2e79e692
> Step 6/10 : RUN wget "https://github.com/mozilla/geckodriver/releases/download/${GECKODRIVER_VERSION}/geckodriver-${GECKODRIVER_VERSION}-${GECKODRIVER_PLATFORM}.tar.gz"         -O geckodriver.tar.gz     && tar -xf geckodriver.tar.gz -C /usr/bin/     && rm geckodriver.tar.gz
>  ---> Running in 3e8f245f6659
> [91m--2019-04-15 19:07:25--  https://github.com/mozilla/geckodriver/releases/download/v0.22.0/geckodriver-v0.22.0-linux32.tar.gz
> [0m[91mResolving github.com (github.com)... [0m[91m192.30.253.113, 192.30.253.112
> Connecting to github.com (github.com)|192.30.253.113|:443... [0m[91mconnected.
> [0m[91mHTTP request sent, awaiting response... [0m[91m302 Found
> Location: https://github-production-release-asset-2e65be.s3.amazonaws.com/25354393/67809a80-b94a-11e8-94f9-f96178c21679?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAIWNJYAX4CSVEH53A%2F20190415%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20190415T190725Z&X-Amz-Expires=300&X-Amz-Signature=8a7b286df9afb7bcbb5b7eef0515293b48493c1ccc3e751bf59b432d4f170f4d&X-Amz-SignedHeaders=host&actor_id=0&response-content-disposition=attachment%3B%20filename%3Dgeckodriver-v0.22.0-linux32.tar.gz&response-content-type=application%2Foctet-stream [following]
> [0m[91m--2019-04-15 19:07:25--  https://github-production-release-asset-2e65be.s3.amazonaws.com/25354393/67809a80-b94a-11e8-94f9-f96178c21679?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAIWNJYAX4CSVEH53A%2F20190415%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20190415T190725Z&X-Amz-Expires=300&X-Amz-Signature=8a7b286df9afb7bcbb5b7eef0515293b48493c1ccc3e751bf59b432d4f170f4d&X-Amz-SignedHeaders=host&actor_id=0&response-content-disposition=attachment%3B%20filename%3Dgeckodriver-v0.22.0-linux32.tar.gz&response-content-type=application%2Foctet-stream
> Resolving github-production-release-asset-2e65be.s3.amazonaws.com (github-production-release-asset-2e65be.s3.amazonaws.com)... [0m[91m52.216.109.219
> Connecting to github-production-release-asset-2e65be.s3.amazonaws.com (github-production-release-asset-2e65be.s3.amazonaws.com)|52.216.109.219|:443... [0m[91mconnected.
> [0m[91mHTTP request sent, awaiting response... [0m[91m200 OK
> Length: 3890915 (3.7M) [application/octet-stream]
> [0m[91mSaving to: â€˜geckodriver.tar.gzâ€™
> 
>      0K .......... ......[0m[91m.... .......... .......... ..........  1% 1.60M 2s[0m[91m
>   3750K .......... .......... .......... .......... ......... 100%  439M=0.3s
> 
> 2019-04-15 19:07:26 (13.1 MB/s) - â€˜geckodriver.tar.gzâ€™ saved [3890915/3890915]
> 
> [0m ---> cce397dc3165
> Removing intermediate container 3e8f245f6659
> Step 7/10 : RUN wget "https://download.mozilla.org/?product=firefox-latest&os=linux64&lang=en-US" -O firefox.tar.bz2     && mkdir /opt/firefox     && tar -xf firefox.tar.bz2 -C /opt/     && ln -s /opt/firefox/firefox /usr/bin/firefox     && rm firefox.tar.bz2
>  ---> Running in ec651c69b7c3
> [91m--2019-04-15 19:07:27--  https://download.mozilla.org/?product=firefox-latest&os=linux64&lang=en-US
> [0m[91mResolving download.mozilla.org (download.mozilla.org)... [0m[91m54.243.11.71, 34.238.86.180, 34.194.44.79, ...
> Connecting to download.mozilla.org (download.mozilla.org)|54.243.11.71|:443... [0m[91mconnected.
> [0m[91mHTTP request sent, awaiting response... [0m[91m302 Found
> Location: https://download-installer.cdn.mozilla.net/pub/firefox/releases/66.0.3/linux-x86_64/en-US/firefox-66.0.3.tar.bz2 [following]
> --2019-04-15 19:07:27--  https://download-installer.cdn.mozilla.net/pub/firefox/releases/66.0.3/linux-x86_64/en-US/firefox-66.0.3.tar.bz2
> Resolving download-installer.cdn.mozilla.net (download-installer.cdn.mozilla.net)... [0m[91m13.249.134.62, 2600:9000:2041:9000:e:19d6:1546:5981, 2600:9000:2041:2000:e:19d6:1546:5981, ...
> Connecting to download-installer.cdn.mozilla.net (download-installer.cdn.mozilla.net)|13.249.134.62|:443... [0m[91mconnected.
> [0m[91mHTTP request sent, awaiting response... [0m[91m200 OK
> Length: 61994174 (59M) [application/x-tar]
> [0m[91mSaving to: â€˜firefox.tar.bz2â€™
> [0m[91m
>      0K .......... ......[0m[91m.... .......... ..[0m[91m........ ........[0m[91m..  0% 3.26M 18s
>  60500K .......... ..[0m[91m........ .......... ......[0m[91m..[0m[91m.. .      [0m[91m   100% 76.2M=0.6s
> 
> 2019-04-15 19:07:28 (99.7 MB/s) - â€˜firefox.tar.bz2â€™ saved [61994174/61994174]
> 
> [0m ---> 514b39e97297
> Removing intermediate container ec651c69b7c3
> Step 8/10 : ENV SELENIUM_PROMISE_MANAGER 0
>  ---> Running in d385041f4f93
>  ---> 79dbd1695921
> Removing intermediate container d385041f4f93
> Step 9/10 : ENV HEADLESS 1
>  ---> Running in 1ee2b85cdb29
>  ---> d4276f459fd1
> Removing intermediate container 1ee2b85cdb29
> Step 10/10 : CMD sh scripts/selenium_startup.sh
>  ---> Running in fbc2e9458966
>  ---> 8ea83c5271ba
> Removing intermediate container fbc2e9458966
> Successfully built 8ea83c5271ba
> Successfully tagged cphb/assignment10-selenium:latest
> travis_time:end:108b99bf:start=1555355215656524134,finish=1555355261564046727,duration=45907522593
> [0Ktravis_fold:end:before_install.2
> [0Ktravis_fold:start:install
> [0Ktravis_time:start:0b7946fc
> [0K$ true
> travis_time:end:0b7946fc:start=1555355261568934640,finish=1555355261571818278,duration=2883638
> [0Ktravis_fold:end:install
> [0Ktravis_time:start:1dee0f4f
> [0K$ echo $TRAVIS_BUILD_DIR
> /home/travis/build/cphjs/soft2019-test_assignment_10
> travis_time:end:1dee0f4f:start=1555355261576114157,finish=1555355261579060369,duration=2946212
> [0K[32;1mThe command "echo $TRAVIS_BUILD_DIR" exited with 0.[0m
> 
> travis_time:start:075b30d0
> [0K$ docker run --rm -v ${TRAVIS_BUILD_DIR}:/app -v $HOME/.m2:/root/.m2 -w /app cphb/assignment10-app mvn -Dtest=cphb.AccountTest,cphb.CreditCardTest test
> [INFO] Scanning for projects...
> [INFO] 
> [INFO] -------------------< cphb.assignments:Assignment_10 >-------------------
> [INFO] Building Assignment_10 Maven Webapp 1.0-SNAPSHOT
> [INFO] --------------------------------[ war ]---------------------------------
> [INFO] 
> [INFO] --- maven-resources-plugin:2.6:resources (default-resources) @ Assignment_10 ---
> [INFO] Using 'UTF-8' encoding to copy filtered resources.
> [INFO] Copying 1 resource
> [INFO] 
> [INFO] --- maven-compiler-plugin:3.1:compile (default-compile) @ Assignment_10 ---
> [INFO] Changes detected - recompiling the module!
> [INFO] Compiling 9 source files to /app/target/classes
> [INFO] 
> [INFO] --- maven-resources-plugin:2.6:testResources (default-testResources) @ Assignment_10 ---
> [INFO] Using 'UTF-8' encoding to copy filtered resources.
> [INFO] skip non existing resourceDirectory /app/src/test/resources
> [INFO] 
> [INFO] --- maven-compiler-plugin:3.1:testCompile (default-testCompile) @ Assignment_10 ---
> [INFO] Changes detected - recompiling the module!
> [INFO] Compiling 7 source files to /app/target/test-classes
> [INFO] 
> [INFO] --- maven-surefire-plugin:2.22.1:test (default-test) @ Assignment_10 ---
> [INFO] 
> [INFO] -------------------------------------------------------
> [INFO]  T E S T S
> [INFO] -------------------------------------------------------
> [INFO] Running cphb.AccountTest
> [INFO] Tests run: 12, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.27 s - in cphb.AccountTest
> [INFO] Running cphb.CreditCardTest
> [INFO] Tests run: 8, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.008 s - in cphb.CreditCardTest
> [INFO] 
> [INFO] Results:
> [INFO] 
> [INFO] Tests run: 20, Failures: 0, Errors: 0, Skipped: 0
> [INFO] 
> [INFO] ------------------------------------------------------------------------
> [INFO] BUILD SUCCESS
> [INFO] ------------------------------------------------------------------------
> [INFO] Total time:  5.351 s
> [INFO] Finished at: 2019-04-15T19:07:48Z
> [INFO] ------------------------------------------------------------------------
> travis_time:end:075b30d0:start=1555355261583502821,finish=1555355269162892417,duration=7579389596
> [0K[32;1mThe command "docker run --rm -v ${TRAVIS_BUILD_DIR}:/app -v $HOME/.m2:/root/.m2 -w /app cphb/assignment10-app mvn -Dtest=cphb.AccountTest,cphb.CreditCardTest test" exited with 0.[0m
> 
> travis_time:start:257e66f5
> [0K$ docker run --rm -v ${TRAVIS_BUILD_DIR}:/app -v $HOME/.m2:/root/.m2 -w /app cphb/assignment10-app mvn -Dtest=cphb.AccountContrllerTest test
> [INFO] Scanning for projects...
> [INFO] 
> [INFO] -------------------< cphb.assignments:Assignment_10 >-------------------
> [INFO] Building Assignment_10 Maven Webapp 1.0-SNAPSHOT
> [INFO] --------------------------------[ war ]---------------------------------
> [INFO] 
> [INFO] --- maven-resources-plugin:2.6:resources (default-resources) @ Assignment_10 ---
> [INFO] Using 'UTF-8' encoding to copy filtered resources.
> [INFO] Copying 1 resource
> [INFO] 
> [INFO] --- maven-compiler-plugin:3.1:compile (default-compile) @ Assignment_10 ---
> [INFO] Nothing to compile - all classes are up to date
> [INFO] 
> [INFO] --- maven-resources-plugin:2.6:testResources (default-testResources) @ Assignment_10 ---
> [INFO] Using 'UTF-8' encoding to copy filtered resources.
> [INFO] skip non existing resourceDirectory /app/src/test/resources
> [INFO] 
> [INFO] --- maven-compiler-plugin:3.1:testCompile (default-testCompile) @ Assignment_10 ---
> [INFO] Nothing to compile - all classes are up to date
> [INFO] 
> [INFO] --- maven-surefire-plugin:2.22.1:test (default-test) @ Assignment_10 ---
> [INFO] 
> [INFO] -------------------------------------------------------
> [INFO]  T E S T S
> [INFO] -------------------------------------------------------
> [INFO] Running cphb.AccountContrllerTest
> 19:07:54,200 |-INFO in ch.qos.logback.classic.LoggerContext[default] - Could NOT find resource [logback-test.xml]
> 19:07:54,200 |-INFO in ch.qos.logback.classic.LoggerContext[default] - Could NOT find resource [logback.groovy]
> 19:07:54,200 |-INFO in ch.qos.logback.classic.LoggerContext[default] - Found resource [logback.xml] at [file:/app/target/classes/logback.xml]
> 19:07:54,278 |-INFO in ch.qos.logback.classic.joran.action.ConfigurationAction - debug attribute not set
> 19:07:54,279 |-INFO in ch.qos.logback.core.joran.action.AppenderAction - About to instantiate appender of type [ch.qos.logback.core.ConsoleAppender]
> 19:07:54,292 |-INFO in ch.qos.logback.core.joran.action.AppenderAction - Naming appender as [STDOUT]
> 19:07:54,344 |-WARN in ch.qos.logback.core.ConsoleAppender[STDOUT] - This appender no longer admits a layout as a sub-component, set an encoder instead.
> 19:07:54,344 |-WARN in ch.qos.logback.core.ConsoleAppender[STDOUT] - To ensure compatibility, wrapping your layout in LayoutWrappingEncoder.
> 19:07:54,344 |-WARN in ch.qos.logback.core.ConsoleAppender[STDOUT] - See also http://logback.qos.ch/codes.html#layoutInsteadOfEncoder for details
> 19:07:54,345 |-INFO in ch.qos.logback.classic.joran.action.LoggerAction - Setting level of logger [cphb.assignment_10] to DEBUG
> 19:07:54,345 |-INFO in ch.qos.logback.classic.joran.action.LoggerAction - Setting additivity of logger [cphb.assignment_10] to false
> 19:07:54,345 |-INFO in ch.qos.logback.core.joran.action.AppenderRefAction - Attaching appender named [STDOUT] to Logger[cphb.assignment_10]
> 19:07:54,346 |-INFO in ch.qos.logback.classic.joran.action.RootLoggerAction - Setting level of ROOT logger to ERROR
> 19:07:54,346 |-INFO in ch.qos.logback.core.joran.action.AppenderRefAction - Attaching appender named [STDOUT] to Logger[ROOT]
> 19:07:54,346 |-INFO in ch.qos.logback.classic.joran.action.ConfigurationAction - End of configuration.
> 19:07:54,348 |-INFO in ch.qos.logback.classic.joran.JoranConfigurator@79ca92b9 - Registering current configuration as safe fallback point
> 
> [INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 1.015 s - in cphb.AccountContrllerTest
> [INFO] 
> [INFO] Results:
> [INFO] 
> [INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0
> [INFO] 
> [INFO] ------------------------------------------------------------------------
> [INFO] BUILD SUCCESS
> [INFO] ------------------------------------------------------------------------
> [INFO] Total time:  4.470 s
> [INFO] Finished at: 2019-04-15T19:07:55Z
> [INFO] ------------------------------------------------------------------------
> travis_time:end:257e66f5:start=1555355269168464539,finish=1555355275778281737,duration=6609817198
> [0K[32;1mThe command "docker run --rm -v ${TRAVIS_BUILD_DIR}:/app -v $HOME/.m2:/root/.m2 -w /app cphb/assignment10-app mvn -Dtest=cphb.AccountContrllerTest test" exited with 0.[0m
> 
> travis_time:start:007f0592
> [0K$ docker network create cphb-network
> e0b87756f884b1107c1b0d2d6d170309b1f212f254c62dbea5f0d1cf75cf6360
> travis_time:end:007f0592:start=1555355275787210923,finish=1555355275906908579,duration=119697656
> [0K[32;1mThe command "docker network create cphb-network" exited with 0.[0m
> 
> travis_time:start:367636e0
> [0K$ docker run --rm -v ${TRAVIS_BUILD_DIR}:/app -w /app -v $HOME/.m2:/root/.m2 --network cphb-network -d --name assignment10-app cphb/assignment10-app
> 86736044b9f7d83466fcab60cf5bbe403684bf3426826c1e62e46a0b4406bba5
> travis_time:end:367636e0:start=1555355275912401263,finish=1555355276207347121,duration=294945858
> [0K[32;1mThe command "docker run --rm -v ${TRAVIS_BUILD_DIR}:/app -w /app -v $HOME/.m2:/root/.m2 --network cphb-network -d --name assignment10-app cphb/assignment10-app" exited with 0.[0m
> 
> travis_time:start:0e9f0c8b
> [0K$ docker run --rm -v ${TRAVIS_BUILD_DIR}:/app -w /app -v $HOME/.m2:/root/.m2 --network cphb-network --name assignment10-selenium --env "APP_URL=http://app.test:8080" --link "assignment10-app:app.test" --name assignment10-selenium cphb/assignment10-selenium
> app.test [172.18.0.2] 8080 (http-alt) : Connection refused
> Waiting for app to start
> app.test [172.18.0.2] 8080 (http-alt) : Connection refused
> Waiting for app to start
> app.test [172.18.0.2] 8080 (http-alt) : Connection refused
> Waiting for app to start
> app.test [172.18.0.2] 8080 (http-alt) : Connection refused
> Waiting for app to start
> app.test [172.18.0.2] 8080 (http-alt) : Connection refused
> Waiting for app to start
> app.test [172.18.0.2] 8080 (http-alt) : Connection refused
> Waiting for app to start
> app.test [172.18.0.2] 8080 (http-alt) : Connection refused
> Waiting for app to start
> app.test [172.18.0.2] 8080 (http-alt) : Connection refused
> Waiting for app to start
> app.test [172.18.0.2] 8080 (http-alt) : Connection refused
> Waiting for app to start
> app.test [172.18.0.2] 8080 (http-alt) : Connection refused
> Waiting for app to start
> app.test [172.18.0.2] 8080 (http-alt) : Connection refused
> Waiting for app to start
> app.test [172.18.0.2] 8080 (http-alt) : Connection refused
> Waiting for app to start
> Waiting for app to start
> app.test [172.18.0.2] 8080 (http-alt) : Connection refused
> app.test [172.18.0.2] 8080 (http-alt) : Connection refused
> Waiting for app to start
> app.test [172.18.0.2] 8080 (http-alt) : Connection refused
> Waiting for app to start
> [INFO] Scanning for projects...
> [INFO] 
> [INFO] -------------------< cphb.assignments:Assignment_10 >-------------------
> [INFO] Building Assignment_10 Maven Webapp 1.0-SNAPSHOT
> [INFO] --------------------------------[ war ]---------------------------------
> [INFO] 
> [INFO] --- maven-resources-plugin:2.6:resources (default-resources) @ Assignment_10 ---
> [INFO] Using 'UTF-8' encoding to copy filtered resources.
> [INFO] Copying 1 resource
> [INFO] 
> [INFO] --- maven-compiler-plugin:3.1:compile (default-compile) @ Assignment_10 ---
> [INFO] Nothing to compile - all classes are up to date
> [INFO] 
> [INFO] --- maven-resources-plugin:2.6:testResources (default-testResources) @ Assignment_10 ---
> [INFO] Using 'UTF-8' encoding to copy filtered resources.
> [INFO] skip non existing resourceDirectory /app/src/test/resources
> [INFO] 
> [INFO] --- maven-compiler-plugin:3.1:testCompile (default-testCompile) @ Assignment_10 ---
> [INFO] Nothing to compile - all classes are up to date
> [INFO] 
> [INFO] --- maven-surefire-plugin:2.22.1:test (default-test) @ Assignment_10 ---
> [INFO] 
> [INFO] -------------------------------------------------------
> [INFO]  T E S T S
> [INFO] -------------------------------------------------------
> [INFO] Running cphb.OpenAccountTest
> 19:08:22,476 |-INFO in ch.qos.logback.classic.LoggerContext[default] - Could NOT find resource [logback-test.xml]
> 19:08:22,476 |-INFO in ch.qos.logback.classic.LoggerContext[default] - Could NOT find resource [logback.groovy]
> 19:08:22,477 |-INFO in ch.qos.logback.classic.LoggerContext[default] - Found resource [logback.xml] at [file:/app/target/classes/logback.xml]
> 19:08:22,599 |-INFO in ch.qos.logback.classic.joran.action.ConfigurationAction - debug attribute not set
> 19:08:22,600 |-INFO in ch.qos.logback.core.joran.action.AppenderAction - About to instantiate appender of type [ch.qos.logback.core.ConsoleAppender]
> 19:08:22,616 |-INFO in ch.qos.logback.core.joran.action.AppenderAction - Naming appender as [STDOUT]
> 19:08:22,705 |-WARN in ch.qos.logback.core.ConsoleAppender[STDOUT] - This appender no longer admits a layout as a sub-component, set an encoder instead.
> 19:08:22,705 |-WARN in ch.qos.logback.core.ConsoleAppender[STDOUT] - To ensure compatibility, wrapping your layout in LayoutWrappingEncoder.
> 19:08:22,705 |-WARN in ch.qos.logback.core.ConsoleAppender[STDOUT] - See also http://logback.qos.ch/codes.html#layoutInsteadOfEncoder for details
> 19:08:22,706 |-INFO in ch.qos.logback.classic.joran.action.LoggerAction - Setting level of logger [cphb.assignment_10] to DEBUG
> 19:08:22,706 |-INFO in ch.qos.logback.classic.joran.action.LoggerAction - Setting additivity of logger [cphb.assignment_10] to false
> 19:08:22,707 |-INFO in ch.qos.logback.core.joran.action.AppenderRefAction - Attaching appender named [STDOUT] to Logger[cphb.assignment_10]
> 19:08:22,707 |-INFO in ch.qos.logback.classic.joran.action.RootLoggerAction - Setting level of ROOT logger to ERROR
> 19:08:22,707 |-INFO in ch.qos.logback.core.joran.action.AppenderRefAction - Attaching appender named [STDOUT] to Logger[ROOT]
> 19:08:22,707 |-INFO in ch.qos.logback.classic.joran.action.ConfigurationAction - End of configuration.
> 19:08:22,708 |-INFO in ch.qos.logback.classic.joran.JoranConfigurator@2d9d4f9d - Registering current configuration as safe fallback point
> 
> app url is http://app.test:8080
> app url is http://app.test:8080
> 1555355315180	Marionette	DEBUG	Remote service is inactive
> [INFO] Tests run: 2, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 13.203 s - in cphb.OpenAccountTest
> [INFO] 
> [INFO] Results:
> [INFO] 
> [INFO] Tests run: 2, Failures: 0, Errors: 0, Skipped: 0
> [INFO] 
> [INFO] ------------------------------------------------------------------------
> [INFO] BUILD SUCCESS
> [INFO] ------------------------------------------------------------------------
> [INFO] Total time:  19.839 s
> [INFO] Finished at: 2019-04-15T19:08:35Z
> [INFO] ------------------------------------------------------------------------
> travis_time:end:0e9f0c8b:start=1555355276214721215,finish=1555355316168624691,duration=39953903476
> [0K[32;1mThe command "docker run --rm -v ${TRAVIS_BUILD_DIR}:/app -w /app -v $HOME/.m2:/root/.m2 --network cphb-network --name assignment10-selenium --env "APP_URL=http://app.test:8080" --link "assignment10-app:app.test" --name assignment10-selenium cphb/assignment10-selenium" exited with 0.[0m
> 
> travis_fold:start:cache.2
> [0Kstore build cache
> travis_time:start:2e3feef8
> [0Ktravis_time:end:2e3feef8:start=1555355316173895094,finish=1555355316177092892,duration=3197798
> [0Ktravis_time:start:091e9393
> [0K[32;1mchange detected (content changed, file is created, or file is deleted):
> /home/travis/.m2/copy_reference_file.log
> /home/travis/.m2/repository/asm/asm/3.0/asm-3.0.jar
> /home/travis/.m2/repository/asm/asm/3.0/asm-3.0.jar.sha1
> /home/travis/.m2/repository/asm/asm/3.0/asm-3.0.pom
> /home/travis/.m2/repository/asm/asm/3.0/asm-3.0.pom.sha1
> /home/travis/.m2/repository/asm/asm/3.0/_remote.repositories
> /home/travis/.m2/repository/asm/asm-parent/3.0/asm-parent-3.0.pom
> /home/travis/.m2/repository/asm/asm-parent/3.0/asm-parent-3.0.pom.sha1
> /home/travis/.m2/repository/asm/asm-parent/3.0/_remote.repositories
> /home/travis/.m2/repository/com/github/jsimone/webapp-runner/9.0.17.0/_remote.repositories
> /home/travis/.m2/repository/com/github/jsimone/webapp-runner/9.0.17.0/webapp-runner-9.0.17.0.jar
> /home/travis/.m2/repository/com/github/jsimone/webapp-runner/9.0.17.0/webapp-runner-9.0.17.0.jar.sha1
> /home/travis/.m2/repository/commons-beanutils/commons-beanutils/1.6/commons-beanutils-1.6.pom
> /home/travis/.m2/repository/commons-beanutils/commons-beanutils/1.6/commons-beanutils-1.6.po
> [0m
> [32;1m...
> [0m
> [32;1mchanges detected, packing new archive[0m
> [32;1muploading master/cache-linux-trusty-e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855.tgz[0m
> [32;1mcache uploaded[0m
> travis_time:end:091e9393:start=1555355316181203598,finish=1555355324082483270,duration=7901279672
> [0Ktravis_fold:end:cache.2
> [0K
> travis_fold:start:dpl_0
> [0Ktravis_time:start:13e7d0fa
> [0K$ rvm $(travis_internal_ruby) --fuzzy do ruby -S gem install dpl
> Fetching dpl-1.10.8.gem
> Successfully installed dpl-1.10.8
> Parsing documentation for dpl-1.10.8
> Installing ri documentation for dpl-1.10.8
> Done installing documentation for dpl after 0 seconds
> 1 gem installed
> travis_time:end:13e7d0fa:start=1555355325209802929,finish=1555355326914615991,duration=1704813062
> [0Ktravis_fold:end:dpl_0
> [0Ktravis_time:start:0e9be22c
> [0Ktravis_fold:start:dpl.1
> [33mInstalling deploy dependencies[0m
> Fetching dpl-heroku-1.10.8.gem
> Fetching multipart-post-2.0.0.gem
> Fetching rendezvous-0.1.2.gem
> Fetching netrc-0.11.0.gem
> Fetching faraday-0.15.4.gem
> Successfully installed multipart-post-2.0.0
> Successfully installed faraday-0.15.4
> Successfully installed rendezvous-0.1.2
> Successfully installed netrc-0.11.0
> Successfully installed dpl-heroku-1.10.8
> invalid options: -SHN
> (invalid options are ignored)
> Parsing documentation for multipart-post-2.0.0
> Installing ri documentation for multipart-post-2.0.0
> Parsing documentation for faraday-0.15.4
> Installing ri documentation for faraday-0.15.4
> Parsing documentation for rendezvous-0.1.2
> Installing ri documentation for rendezvous-0.1.2
> Parsing documentation for netrc-0.11.0
> Installing ri documentation for netrc-0.11.0
> Parsing documentation for dpl-heroku-1.10.8
> Installing ri documentation for dpl-heroku-1.10.8
> Done installing documentation for multipart-post, faraday, rendezvous, netrc, dpl-heroku after 1 seconds
> 5 gems installed
> 
> travis_fold:end:dpl.1
> travis_fold:start:dpl.2
> [33mPreparing deploy[0m
> authentication succeeded
> checking for app cphb-assignment10
> found app cphb-assignment10
> 
> travis_fold:end:dpl.2
> travis_fold:start:dpl.3
> [33mDeploying application[0m
> creating application archive
> uploading application archive
> triggering new deployment
> 
> -----> Java app detected
> -----> Installing JDK 1.8... done
> -----> Installing Maven 3.3.9... done
> -----> Executing: mvn -DskipTests clean dependency:list install
>        [INFO] Scanning for projects..
>        [INFO] ------------------------------------------------------------------------
>        [INFO] BUILD SUCCESS
>        [INFO] ------------------------------------------------------------------------
>        [INFO] Total time: 10.007 s
>        [INFO] Finished at: 2019-04-15T19:09:13+00:00
>        [INFO] Final Memory: 24M/171M
>        [INFO] ------------------------------------------------------------------------
> -----> Discovering process types
>        Procfile declares types -> web
> 
> -----> Compressing...
>        Done: 90.2M
> -----> Launching...
>        Released v4
>        https://cphb-assignment10.herokuapp.com/ deployed to Heroku
> 
> 
> travis_fold:end:dpl.3
> travis_time:end:0e9be22c:start=1555355326921349133,finish=1555355396986039886,duration=70064690753
> [0K
> Done. Your build exited with 0.
> 