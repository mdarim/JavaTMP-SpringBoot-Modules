# Spring Boot Kubernetes Module

- Download kubectl - The Kubernetes command-line tool, kubectl, allows you to run commands
  against Kubernetes clusters.
- Install `kubectl` on Windows
- Check you Architecture by `echo %PROCESSOR_ARCHITECTURE%`
- Verify by `kubectl cluster-info`
- All Kubernetes container images are deployed to the registry.k8s.io container image
  registry.
- `registry.k8s.io/kube-apiserver:v1.32.0`
- `registry.k8s.io/kube-controller-manager:v1.32.0`
- `registry.k8s.io/kube-proxy:v1.32.0`
- `registry.k8s.io/kube-scheduler:v1.32.0`
- `registry.k8s.io/conformance:v1.32.0`
- pull a dedicated architecture by suffixing the container image name,
  for example `registry.k8s.io/kube-apiserver-amd64:v1.32.0.`
- Verify the installation: `kubectl version --client`

##  

To install and run Kubernetes locally on a Windows AMD64 machine, follow these steps:

**1. Install `kubectl`**

`kubectl` is the command-line tool that allows you to interact with your Kubernetes
cluster.

- **Download the latest `kubectl` binary:**
  Open Command Prompt or PowerShell and execute:
  ```powershell
  curl.exe -LO "https://dl.k8s.io/release/v1.32.0/bin/windows/amd64/kubectl.exe"
  ```


- **Optional: Validate the binary:**
  Download the checksum file:
  ```powershell
  curl.exe -LO "https://dl.k8s.io/v1.32.0/bin/windows/amd64/kubectl.exe.sha256"
  ```
  Verify the binary against the checksum:
  ```powershell
  CertUtil -hashfile kubectl.exe SHA256
  type kubectl.exe.sha256
  ```
  Ensure the computed hash matches the checksum.

- **Add `kubectl` to your system `PATH`:**
  Place the `kubectl.exe` binary in a directory included in your system's `PATH` or update
  the `PATH` environment variable to include the directory where `kubectl.exe` resides.

- **Verify the installation:**
  ```powershell
  kubectl version --client
  ```
  This command should display the client version of `kubectl`.

**2. Install Minikube**

Minikube allows you to run a single-node Kubernetes cluster locally.

- **Install Minikube using Windows Package Manager:**
  If you have `winget` installed, execute:
  ```powershell
  winget install minikube
  ```

**3. Start Minikube**

Minikube requires a hypervisor to create virtual machines. Ensure you have one of the
following installed:

- **Hyper-V:**
  Available on Windows 10 Enterprise, Pro, and Education editions. To enable Hyper-V:
    - Press the Windows key and search for "Turn Windows features on or off".
    - Check the boxes for Hyper-V and Windows Hypervisor Platform.
    - Click OK and restart your computer.


- **Docker:**
  If you have Docker installed, you can use it as the driver.

- **VirtualBox:**
  Download and install VirtualBox from the official website.

To start Minikube with a specific driver, run:

```powershell
minikube start --driver=<driver_name>
```

Replace `<driver_name>` with `hyperv`, `docker`, or `virtualbox` depending on your setup.

**4. Verify Minikube Status**

After starting Minikube, check its status:

```powershell
minikube status
```

This command provides information about the Minikube cluster's state.

**5. Interact with the Cluster**

With Minikube running, you can use `kubectl` to interact with your local Kubernetes
cluster. For example:

- **View cluster information:**
  ```powershell
  kubectl cluster-info
  ```

- **List nodes:**
  ```powershell
  kubectl get nodes
  ```

By following these steps, you can set up and run Kubernetes locally on your Windows AMD64
machine.

----

## References

- [Spring Boot Kubernetes](https://spring.io/guides/gs/spring-boot-kubernetes)
- [Download Kubernetes](https://kubernetes.io/releases/download/)
