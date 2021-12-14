pipeline {

    agent any

    environment {
        TERRAFORM = 'Terraform'
    }

    stages {

        stage('Set ssh key in cloud-init') {
            steps {
                script {
                    sh "sed -i 's/SSH_KEY/${params.PUBLIC_SSH}/g' ${params.TERRAFORM_PATH}/scripts/add-ssh-web-app.yaml"
                }
            }
        }

        stage('Terraform init') {
            steps {
                dir("/usr/share/terraform/instances/"){
                    sh 'terraform init'
                }

            }
        }

        stage('Terraform apply') {
            steps {
                dir("${params.TERRAFORM_PATH}/instances/"){

                    sh 'terraform apply --auto-approve'

                }
            }
        }


    }
}