pipeline {
    agent any
    tools{
        maven 'Maven'
    }
    stages {
        stage('unit tests') {
               steps {
                echo "starts running unit testing"
                bat "mvn clean compile test"
            }
        }

        stage('integration tests') {
                       steps {
                        echo "starts running integration testing"
                        bat "mvn -Pfailsafe verify"
                    }
        }

        stage('functional cucumber tests') {
                        steps {
                         echo "starts running functional testing"
                         bat "mvn -pl hospital-functional-tests -Dtest=RunnerTest test"


                        }
        }
         stage('hospital build jar') {
                        steps {
                         echo "building project"
                         bat "mvn -pl hospital-main clean package"
                               }
         }

         stage('sonarqube verify') {
                        steps {
                         echo "checking project by sonarqube"
//                          withSonarQubeEnv('SonarQube')
                         bat "mvn -pl hospital-main clean verify sonar:sonar"
                              }
                  }

         stage('build docker image') {
                        steps {
                         echo "building docker image"
                         bat "docker build . -t alekckorsh/hospital"
                              }
         }

         stage('deploy docker image') {
                                 steps {
                                  echo "building docker image"
                                  bat "docker push alekckorsh/hospital"


                                       }
         }

    }
}
