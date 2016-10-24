package demo;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.openstack4j.api.OSClient.OSClientV2;
import org.openstack4j.api.types.ServiceType;
import org.openstack4j.model.compute.Flavor;
import org.openstack4j.model.compute.Image;
import org.openstack4j.model.storage.block.Volume;
import org.openstack4j.model.storage.block.VolumeType;
import org.openstack4j.openstack.OSFactory;

public class Infrastructure {

	private static HashMap<String, String> usersMapping;

	public static void main(String[] args) throws IOException {
		// Utility class
		Utility utils = new Utility();

		// Read users file
		usersMapping = utils.readConfigurationFile("config.properties");

		OSClientV2 os = OSFactory.builderV2().endpoint("https://ops.elastx.net:5000/v2.0")
				.credentials(usersMapping.get("openstack.username"), usersMapping.get("openstack.pwd"))
				.tenantName(usersMapping.get("openstack.tenantName")).authenticate();

		// Print supported services
		System.out.println("### Supported services ###");
		Set<ServiceType> supportedServices = os.getSupportedServices();
		for (ServiceType serviceType : supportedServices) {
			System.out.println(
					"Service name: " + serviceType.getServiceName() + "Service type: " + serviceType.getType());
		}

		// Print list of flavors
		System.out.println("\n\n\n");
		System.out.println("####  Flavors ####");
		// Find all Flavors
		List<? extends Flavor> flavors = os.compute().flavors().list();
		for (Flavor flavor : flavors) {
			System.out.format("Flavor id: %s, Flavor name: %s; vCPUs: %d; Local disk size: %d;GB Ram: %dMB \n",
					flavor.getId(), flavor.getName(), flavor.getVcpus(), flavor.getDisk(), flavor.getRam());
		}

		// Print list of available images
		System.out.println("\n\n\n");
		System.out.println("####  Images ####");
		List<? extends Image> images = os.compute().images().list();
		for (Image image : images) {
			System.out.format("Image id: %s, Image name: %s; Min disk: %d; Min ram: %d \n", image.getId(),
					image.getName(), image.getMinDisk(), image.getMinRam());
		}
		
		// Print detailed information on images 
		System.out.println("\n\n\n");
		System.out.println("####  Images details ####");
		List<? extends org.openstack4j.model.image.Image> glanceImages = os.images().list();
		for (org.openstack4j.model.image.Image image : glanceImages) {
			System.out.format("Image name: %s; Disk format: %s; Size: %d \n", image.getName(),
					image.getDiskFormat().name(), image.getSize());
		}
		
		// Print Volumes
		System.out.println("\n\n\n");
		System.out.println("####  Volumes types ####");
		List<? extends VolumeType> types = os.blockStorage().volumes().listVolumeTypes();
		for (VolumeType volumeType : types) {
			System.out.format("Type id: %s, Type name: %s \n", volumeType.getId(), volumeType.getName());
		}
		System.out.println("\n\n\n");
		System.out.println("####  Volumes types ####");
		List<? extends Volume> volumes = os.blockStorage().volumes().list();
		for (Volume vol : volumes) {
			System.out.format("Volume id: %s; Volume name: %s; Description: %s; Tenant id: %s\n", vol.getId(), 
					vol.getName(), vol.getDescription(), vol.getTenantId());
		}
	}
}
