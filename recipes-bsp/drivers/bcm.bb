SUMMARY = "Example of how to build an external Linux kernel module"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=12f884d2ae1ff87c09e5b7ccc2c4ca7e"

inherit module

PR = "r0"
PV = "0.1"

SRC_URI = "file://Makefile \
           file://aes256.c \
           file://aes256.h \
           file://bcm_au.c \
           file://bcm_au.rules \
           file://COPYING \
          "

S = "${WORKDIR}"
KV = "${PREFERRED_VERSION_linux-xsarius}"

# The inherit of module.bbclass will automatically name module packages with
# "kernel-module-" prefix as required by the oe-core build environment.

do_install() {
        install -d ${D}/${sysconfdir}/modules-load.d
        install -d ${D}/lib/modules/${KV}/extra
        
        touch ${D}${sysconfdir}/modules-load.d/bcm.conf
        echo bcm >> ${D}/${sysconfdir}/modules-load.d/bcm.conf
        
        for f in ${S}/*.ko; do
        	install -m 0644 $f ${D}/lib/modules/${KV}/extra;
        done
}       
