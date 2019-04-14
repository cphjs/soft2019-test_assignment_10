
## Notes

- Running the selenium tests more than without reloading the web app will fail. This is because the test "opens an account", and it cannot create multiple accounts with the same name.


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