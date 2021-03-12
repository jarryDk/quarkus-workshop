#!/bin/bash

QUARKUS_BUILD=native
VERSION=steep4
REGISTRY_IP=192.168.1.36
CLEAN=true

while [[ $# -gt 1 ]]
do
key="$1"

case $key in
    -b|--qb)
    QUARKUS_BUILD="$2"
    shift # past argument
    ;;
    -v|--version)
    VERSION="$2"
    shift # past argument
    ;;
    -c|--clean)
    CLEAN="$2"
    shift # past argument
    ;;
    -ip)
    REGISTRY_IP="$2"
    shift # past argument
    ;;
    --default)
    DEFAULT=YES
    ;;
    *)
            # unknown option
    ;;
esac
shift # past argument or value
done

echo "-------------------------------"
echo "- QUARKUS_BUILD : '$QUARKUS_BUILD'"
echo "- VERSION : '$VERSION'"
echo "- REGISTRY_IP : '$REGISTRY_IP'"
echo "- CLEAN : '$CLEAN'"
echo "-------------------------------"
echo ""