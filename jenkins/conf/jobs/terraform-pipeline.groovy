pipeline {

    agent any

    environment {
        TERRAFORM = 'Terraform'
    }

    stages {

        stage('Terraform init') {
            steps {
                dir('/usr/share/terraform/instances/'){
                    sh 'terraform init'
                }

            }
        }

        stage('Terraform apply') {
            steps {
                dir('/usr/share/terraform/instances/'){

                    sh 'terraform apply --auto-approve'

                }
            }
        }

        stage('Terraform destroy') {
            when {
                expression { params.SKIP_DESTROY == false }
            }
            steps {
                dir('/usr/share/terraform/instances/'){

                    sh 'terraform destroy --auto-approve'

                }
            }
        }

    }
}