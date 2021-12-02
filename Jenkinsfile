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

    }
}
