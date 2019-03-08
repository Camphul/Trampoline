# trampoline-asset-store

Bridge to store assets(blobs, files, images, etc...). Goal is to easily switch between cloud providers(or local storage).

Metadata on assets is stored through JPA(using trampoline-data)


## Architecture

An `Asset` object contains the raw binary data of the stored file and a `AssetMetaData`.

`AssetMetaData` contains information regarding the `Asset`(such as filename, original name, filetype, and filesize).

`AssetMetaData` is a JPA entity(extends `TrampolineEntity`) which is basically a reference of where the actual binary file is stored.

An `AssetStore` implementation is used to manage the storage of the `Asset` and persists the `AssetMetaData`.

When wanting to return a binary file to the client. You should return an `AssetResponse` object(which can be created from the static `from` method).

Trampoline has a built-in `HttpMessageConverter` which converts the `AssetResponse` into a valid response with the asset data and filetype.