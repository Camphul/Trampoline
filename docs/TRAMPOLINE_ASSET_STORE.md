# trampoline-asset-store docs

[trampoline-asset-store](../trampoline-asset-store) provides an easy way to handle binary assets.
You do not want everything persisted in the database as binary blobs so this stores the asset somewhere, persists an entity which links to the actual asset and also contains some meta-inf regarding the asset.
