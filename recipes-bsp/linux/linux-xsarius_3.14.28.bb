DESCRIPTION = "Linux kernel for ${MACHINE}"
LICENSE = "GPLv2"
SECTION = "kernel"
PACKAGE_ARCH = "${MACHINE_ARCH}"

KV = "3.14.28.1"
PR_INC = ".2"

SRC_URI[md5sum] = "d83e48d38bf83c50f7a175bc6a1fd2ce"
SRC_URI[sha256sum] = "9c04354eba2861304d09bce015b4d381c65f968ba445465a70a77bee3ee16f45"

LIC_FILES_CHKSUM = "file://${WORKDIR}/linux/COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

FILESEXTRAPATHS_prepend := "${THISDIR}/stblinux-${KV}:"

SRC_URI = "http://en3homeftp.net/pub/src/linux-${KV}.tar.xz \
	file://0001.fix_hwtype.patch \
        file://0002.recording_issue.patch \
	file://0001.remove_vtuner_index_check.patch \
        file://bcm_genet_disable_warn.patch \
	file://rtl8712_fix_build_error.patch \
        file://kernel-gcc6.patch \
        file://kernel-gcc7.patch \
	file://kernel-gcc8.patch \
	file://0003-mips-kernel-ilog2-gcc7.patch \
	file://0003-3.x-uaccess-dont-mark-register-as-const.patch \
        file://defconfig \
        "

inherit kernel machine_kernel_pr

S = "${WORKDIR}/linux"

export OS = "Linux"
KERNEL_IMAGETYPE = "zImage"
KERNEL_OUTPUT = "arch/${ARCH}/boot/${KERNEL_IMAGETYPE}"
KERNEL_OBJECT_SUFFIX = "ko"
KERNEL_IMAGEDEST = "tmp"

FILES_kernel-image = "/${KERNEL_IMAGEDEST}/zImage"

do_configure_prepend() {
}

kernel_do_install_append() {
        install -d ${D}/${KERNEL_IMAGEDEST}
        install -m 0755 ${KERNEL_OUTPUT} ${D}/${KERNEL_IMAGEDEST}
}

kernel_do_compile() {
        unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS MACHINE
        oe_runmake ${KERNEL_IMAGETYPE_FOR_MAKE} ${KERNEL_ALT_IMAGETYPE} CC="${KERNEL_CC}" LD="${KERNEL_LD}"
}

pkg_postinst_kernel-image () {
        if [ -d /proc/stb ] ; then
                dd if=/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE} of=/dev/mmcblk0p1
        fi
        rm -f /${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}
        true
}

pkg_postrm_kernel-image () {
}

MACHINE_KERNEL_PR_append = "${PR_INC}.0"

COMPATIBLE_MACHINE = "revo4k|galaxy4k"
