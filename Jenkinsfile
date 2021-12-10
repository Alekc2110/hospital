pipeline {
    agent any
    tools{
        maven 'Maven'
    }
    stages {
        stage('unit tests') {
               steps {
                echo "starts running unit testing"
                bat "mvn -pl hospital-main clean compile test"
            }
        }

        stage('integration tests') {
                       steps {
                        echo "starts running integration testing"
                        bat "mvn -pl hospital-main -Pfailsafe verify"
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
                         echo "verify by sonarqube"
                         bat "mvn -pl hospital-main clean verify sonar:sonar -Dsonar.projectKey=hospital -Dsonar.host.url=http://localhost:9000 -Dsonar.login=5517ea47b308f8bd4d9c5d800046fdb80ba026b7"
//                          bat "mvn -pl hospital-main clean verify sonar:sonar -Dsonar.projectKey=hospital-main -Dsonar.host.url=http://localhost:9000 -Dsonar.login=05d91302657d2edb56fdd3b81e25a968f690907b"
                              }
                  }

         stage('build docker image') {
                        steps {
                         echo "building docker image"
                         bat "docker build . -t alekckorsh/hospital"
                              }
         }

          stage('run docker container alekckorsh/hospital') {
                                 steps {
                                  echo "running docker container"
                                  bat "docker run -d -p 8080:8080 alekckorsh/hospital:latest"
                                       }
                  }

          stage('functional cucumber tests') {
                                 steps {
                                  echo "starts running functional testing"
                                  bat "mvn -pl hospital-functional-tests -Dtest=RunnerTest test"

                                 }
                 }

//          stage('deploy docker image') {
//                                  steps {
//                                   echo "building docker image"
//                                   bat "docker push alekckorsh/hospital"
//
//
//                                        }
//          }

    }
}
