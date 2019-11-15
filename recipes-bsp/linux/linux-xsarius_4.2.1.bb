SUMMARY = "Linux kernel for ${MACHINE}"
LICENSE = "GPLv2"
SECTION = "kernel"
PACKAGE_ARCH = "${MACHINE_ARCH}"

SRCDATE = "20151022"
KV = "4.2.1"

inherit kernel machine_kernel_pr

SRC_URI[md5sum] = "86ed84a17ce276a4db610e6aad753ae4"
SRC_URI[sha256sum] = "3e33599a4eeb6e2eae7ff4ab7ae1f3e7b80f657d7e1437a1d6290b4597ad9853"

LIC_FILES_CHKSUM = "file://${WORKDIR}/linux-${KV}/COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

SRC_URI += "http://en3homeftp.net/pub/src/xsarius-linux-${KV}-${SRCDATE}.tar.gz \
    file://defconfig \
    file://dvbskyt330_si2168_demod.patch \
    file://0002-log2-give-up-on-gcc-constant-optimizations.patch \
    "

S = "${WORKDIR}/linux-${KV}"

export OS = "Linux"
KERNEL_OBJECT_SUFFIX = "ko"
KERNEL_OUTPUT = "vmlinux"
KERNEL_OUTPUT_DIR = "."
KERNEL_IMAGETYPE = "vmlinux"
KERNEL_IMAGEDEST = "tmp"

KERNEL_EXTRA_ARGS = "EXTRA_CFLAGS=-Wno-attribute-alias"
KERNEL_PACKAGE_NAME = "kernel"

FILES_${KERNEL_PACKAGE_NAME}-image = "/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz"

kernel_do_install_append() {
    ${STRIP} ${D}/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}-${KERNEL_VERSION}
    gzip -9c ${D}/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}-${KERNEL_VERSION} > ${D}/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz
    rm -rf ${D}/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}-${KERNEL_VERSION}
}

pkg_postinst_kernel-image () {
    if [ "x$D" == "x" ]; then
        if [ -f /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz ] ; then
            flash_erase /dev/mtd6 0 0
            nandwrite -p /dev/mtd6 /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz
            rm -f /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.gz
        fi
    fi
    true
}

# extra tasks
addtask kernel_link_images after do_compile before do_install
