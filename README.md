# flight-service

## Quick Start

### Azure App Service

#### Prerequisites

- Populate all variables in the `vars` section of the `Taskfile`

- Create a `.env` file and fill it with the following PostgreSQL connection parameters:

  ```sh
  touch .env

  # fill Postges username
  echo 'AZ_PG_USR_VAL="<POSTGRES_USER>"' >> .env

  # fill Postges password
  echo 'AZ_PG_PWD_VAL="<POSTGRES_PASSWORD>"' >> .env
  ```

- Be sure `DBforPostgreSQL` resource provider is registered:
  ```sh
  az provider list \
     --out table \
     --query "[].{Provider:namespace, Status:registrationState}"

  # register the Microsoft.DBforPostgreSQL resource provider if it is not registered
  az provider register --namespace Microsoft.DBforPostgreSQL

  # watch the registration status
  az provider show -n Microsoft.DBforPostgreSQL --output table

  # list resource types in the Microsoft.DBforPostgreSQL resource provider
  az provider show \
      --namespace Microsoft.DBforPostgreSQL \
      --out table \
      --query "resourceTypes[*].resourceType"
  ```

- Be sure `KeyVault` resource provider is registered:
  ```sh
  az provider list \
      --out table \
      --query "[].{Provider:namespace, Status:registrationState}"

  # register the Microsoft.KeyVault resource provider if it is not registered
  az provider register --namespace Microsoft.KeyVault

  # watch the registration status
  az provider show -n Microsoft.KeyVault --output table

  # list resource types in the Microsoft.KeyVault resource provider
  az provider show \
      --namespace Microsoft.KeyVault \
      --out table \
      --query "resourceTypes[*].resourceType"
  ```

#### Set Up Environment

- Create a `Resource Group` and generate the `User ID` and `Subscription ID` variables within the `.task` directory:
  ```sh
  task setup-env
  ```

#### Set Up Network

- Create an `Azure Virtual Network` and the required `subnets` for the database, private endpoints, and web app:
  ```sh
  task setup-network
  ```

#### Set Up Postgres Server

- Create a `PostgreSQL Flexible Server` with private network access by configuring a `Private DNS Zone`, linking it to the Virtual Network, and deploying the server in a delegated subnet:

  ```sh
  task setup-pg
  ```
#### Set Up Key Vault

- Set up an `Azure Key Vault`, securely store PostgreSQL credentials and connection details, and configure private access using `Private Endpoints` and `Private DNS`:

  ```sh
  task setup-kv
  ```

#### Deploy the Web App
- Deploy the application to `Azure App Service`, integrate it into the Virtual Network, enable logging and system-assigned identity, and grant it access to the Key Vault secrets:

  ```sh
  task deploy-webapp
  ```

#### Clean Up Resources
- Clean up all resources created for the Web App by removing local task files, and deleting the Azure Resource Group and Key Vault safely:

  ```sh
  task clean-up
  ```
