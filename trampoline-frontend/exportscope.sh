#!/bin/bash
./test.sh
echo "Tagging"
bit tag --all 1.0.0
echo "Exporting to scope.."
bit export camphul.trampoline
