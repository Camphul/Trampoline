#!/bin/bash
echo "Importing all bit components"
npm config set '@bit:registry' https://node.bitsrc.io

BIT_IMPORT_PATH="components"
bit init

importComponent() {
    bit import $1 --path $BIT_IMPORT_PATH
}

importComponent camphul.trampoline/http
importComponent camphul.trampoline/configuration

bit install