#!/bin/bash
echo "Adding components"
bit add src/**/* --tests 'test/{PARENT}/{FILE_NAME}.spec.js'
