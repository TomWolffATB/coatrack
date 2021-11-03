## Tutorial for system-integration-testing in CoatRack

### Requirements

* Linux Commandline, for Windows use WSL
* Docker installation
* Credentials for a GitHub account and CoatRack Web Application URL - please, insert them in the ```config.properties``` file.



### Scripts

```run.sh```: Executes the tests. The scripts take care of all details in the background like pulling/building images etc.

```stop.sh```: If run.sh is interrupted (e.g. by CTRL + 'c') before terminating, the containers could still run in the detached-mode in the background. Execute stop.sh to terminate them.

```cleanup.sh```: Deletes all possibly running containers and images related to this feature.



### Testing a local CoatRack build with Docker

* ```config.properties```: Change the value of ```host``` to "host.docker.internal".
* Ensure the OAuth2 Application settings ```Homepage URL``` and ```Authorization callback URL``` on GitHub to have the value ```http://host.docker.internal:8080```. Otherwise you will receive a URL mismatch error during the redirection from GitHub login to the CoatRack Web Application.
*  ```application.yml```:
  * Ensure the presence of the correct credentials for the just mentioned OAuth2 application, namely ```spring.security.oauth2.client.registration.github.client-id``` and ```client-secret```.
  * The parameter ```ygg.admin.api-base-url-for-gateway``` should have the value ```http://host.docker.internal:8080/api/```.
  * If a config server is used which overrides the local ```application.yml```, then ensure that it contains the proper parameter values just mentioned.
* Rebuild the whole CoatRack project after all required configurations are done.
* Finally, run the CoatRack Web Application locally and execute ```run.sh```.



### Testing with IntelliJ

* To see what actually happens behind the scenes, you can execute the tests in IntelliJ on a Windows machine only. You also require following installations: 
  * wget and curl (propbably already installed by default, check via CLI)
  * [geckodriver](https://github.com/mozilla/geckodriver/releases) (at least version 0.30)
  * firefox
  * openjdk-11 
* And you have to make them accessible for the CLI by setting the proper Environment Variables (PATH).
