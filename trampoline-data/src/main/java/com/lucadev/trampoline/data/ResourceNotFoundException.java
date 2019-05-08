package com.lucadev.trampoline.data;

import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

/**
 * A {@link RuntimeException} which is to be used when a JPA resource could not be found(unknown id, etc..).
 * <p>
 * This model specifically tells Spring to return a 404 error code to the client.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 7-12-18
 */
@Getter
@ToString
public class ResourceNotFoundException extends ResponseStatusException {

	/**
	 * Construct not found exception
	 */
	public ResourceNotFoundException() {
		this("Could not find resource.");
	}

	/**
	 * Construct the model with only a message describing the error.
	 * It might not be directly related to the resource id but may be a related resource which was not found.
	 * It is however encouraged to then use the {@link #ResourceNotFoundException(UUID, String)} constructor with a clear error description.
	 * This description should inform the client that a related resource to the resouce with the specified id was not found.
	 *
	 * @param message a description of the error message in detail.
	 */
	public ResourceNotFoundException(String message) {
		super(HttpStatus.NOT_FOUND, message);
	}

	/**
	 * Construct the model with the resource id that could not be found.
	 * Uses a default message as error description.
	 *
	 * @param resourceId the id which was used to try and fetch the resource.
	 */
	public ResourceNotFoundException(UUID resourceId) {
		this("Resource with id " + resourceId + " not found");
	}

	/**
	 * Construct the model with the resource id that could not be found together with an informative message.
	 *
	 * @param resourceId the id which was used to try and fetch the resource.
	 * @param message    a description explaining the error in detail.
	 */
	public ResourceNotFoundException(UUID resourceId, String message) {
		this("ID: " + resourceId + " Messaqe: " + message);
	}

}
