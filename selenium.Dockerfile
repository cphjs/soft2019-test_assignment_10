FROM maven:3.6.0-jdk-8

ENV GECKODRIVER_VERSION="v0.22.0"
ENV GECKODRIVER_PLATFORM="linux32"

RUN apt-get update \
    && apt-get install libdbus-glib-1-2 -y

# debug
RUN apt-get install netcat dnsutils -y

RUN wget "https://github.com/mozilla/geckodriver/releases/download/${GECKODRIVER_VERSION}/geckodriver-${GECKODRIVER_VERSION}-${GECKODRIVER_PLATFORM}.tar.gz" \
        -O geckodriver.tar.gz \
    && tar -xf geckodriver.tar.gz -C /usr/bin/ \
    && rm geckodriver.tar.gz

# install firefox
RUN wget "https://download.mozilla.org/?product=firefox-latest&os=linux64&lang=en-US" -O firefox.tar.bz2 \
    && mkdir /opt/firefox \
    && tar -xf firefox.tar.bz2 -C /opt/ \
    && ln -s /opt/firefox/firefox /usr/bin/firefox \
    && rm firefox.tar.bz2

    
ENV SELENIUM_PROMISE_MANAGER=0 
ENV HEADLESS=1

#CMD ["mvn", "-Dtest=cphb.OpenAccountTest", "-DAPP_URL=${APP_URL}",  "test"]
#CMD mvn -Dtest=cphb.OpenAccountTest -DAPP_URL=${APP_URL} test
CMD sh scripts/selenium_startup.sh
