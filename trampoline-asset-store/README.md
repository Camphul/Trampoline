# trampoline-asset-store

AssetStore provides a mechanism implement file uploads and binary files in RESTful API's.

## Architecture

An `Asset` object contains the raw binary data of the stored file and a `AssetMetaData`.

`AssetMetaData` contains information regarding the `Asset`(such as filename, original name, filetype, and filesize).

`AssetMetaData` is a JPA entity(extends `TrampolineEntity`) which is basically a reference of where the actual binary file is stored.

An `AssetStore` implementation is used to manage the storage of the `Asset` and persists the `AssetMetaData`.

When wanting to return a binary file to the client. You should return an `AssetResponse` object(which can be created from the static `from` method).

Trampoline has a built-in `HttpMessageConverter` which converts the `AssetResponse` into a valid response with the asset data and filetype.

## Application events

We have the following Spring events:

- `AssetEvent`
- `AssetEvent$Put`
- `AssetEvent$Remove`

The `AssetEvent$Put` and `AssetEvent$Remove` classes are subclasses of `AssetEvent` which simply define the action type in the constructor.

When `AssetStore#put` is invoked the `AssetEvent$Put` will be published once the asset is stored.

When `AssetStore#remove`  is invoked the `AssetEvent$Remove` will be published. Afterwards it will be deleted.
This is done because we want to pass the `AssetMetaData` in the event before it's deleted.

Publishing either of the two events will not invoke the `AssetStore` to `put` or `remove` an asset.
It simply emits an event when a method on the asset store is invoked.

Custom `AssetStore` implementations which have features other than only `put` and `remove` may call `publish` on the `AssetEventPublisher`.
This requires you to create an `AssetEvent`. You should use `Action.OTHER` in this case.