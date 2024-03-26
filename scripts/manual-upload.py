from azure.storage.blob import BlobServiceClient, BlobClient, ContainerClient
import os 

# Notes to self: enable storage account access
# enable access to the container too :/

# Connection string for your Azure Storage Account
connection_string = ""

# Name of the container in your Storage Account
container_name = ""

# Local directory path of the files you want to upload
local_directory_path = "..\\..\\..\\the-office"

# Create a blob service client
blob_service_client = BlobServiceClient.from_connection_string(connection_string)

# Get or create a container
container_client = blob_service_client.get_container_client(container_name)
container_exists = container_client.exists()

if container_exists:
    print("Container exists.")
else:
    print("Container does not exist.")
    container_client.create_container()

# Function to upload a file to Blob Storage
def upload_file(blob_client, local_file_path):
    with open(local_file_path, "rb") as data:
        blob_client.upload_blob(data)

# Upload all files in the directory to Blob Storage
for root, _, files in os.walk(local_directory_path):
    for file in files:
        local_file_path = os.path.join(root, file)
        blob_name = local_file_path[len(local_directory_path)+1:]  # Remove the common prefix
        blob_client = blob_service_client.get_blob_client(container=container_name, blob=blob_name)
        upload_file(blob_client, local_file_path)

print(f"Uploaded contents of {local_directory_path} to {container_name}")