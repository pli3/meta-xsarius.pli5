SECTION = "base"
LICENSE = "CLOSED"
PRIORITY = "required"

PR = "r7"

SRCDATE = "20161223"

SRC_URI = "http://en3homeftp.net/pub/down/vmlinuz-initrd_${MACHINE}_${SRCDATE}.tar.gz"

FILES_${PN} = "/boot"

INHIBIT_PACKAGE_STRIP = "1"
PACKAGE_ARCH := "${MACHINE_ARCH}"

do_install() {
    install -d ${D}/boot
    install -m 0755 ${WORKDIR}/vmlinuz-initrd-7439b0 ${D}/boot/initrd_run.bin
}

SRC_URI[md5sum] = "b6a51d92759880c892d700b67c3e3685"
SRC_URI[sha256sum] = "58c4b50a471909640f2320c6339f8dd0b2b5997f7a37568cc0ebee215cf09624"
