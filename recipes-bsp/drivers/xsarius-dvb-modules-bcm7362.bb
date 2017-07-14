SUMMARY = "Hardware drivers for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"

PACKAGE_ARCH = "${MACHINEBUILD}"

SRCDATE = "20170714"
KV = "4.2.1"
PV = "${KV}+${SRCDATE}"
PR = "r2"

SRC_URI[md5sum] = "f13ca563fe1a6a3481c9e70c8c054a6f"
SRC_URI[sha256sum] = "cc8099309c91f931e52bb208d3fd4352a9dcf86b1b3d9bf98fed3700c93d4068"

SRC_URI = "http://en3homeftp.net/release/images/oedrivers/bcmlinuxdvb_7362-${KV}-${SRCDATE}.tar.gz"

S = "${WORKDIR}"

INHIBIT_PACKAGE_STRIP = "1"

inherit module

do_compile() {
}

FILES_${PN} += "${sysconfdir}/modules-load.d/_${MACHINE}.conf"

do_install() {
		install -d ${D}/lib/modules/${KV}/extra
		for f in ${S}/lib/modules/${KV}/extra/*.ko; do
			install -m 0644 $f ${D}/lib/modules/${KV}/extra;
		done
		install -d ${D}/${sysconfdir}/modules-load.d
		for i in `ls ${D}/lib/modules/${KV}/extra | grep \\.ko | sed -e 's/.ko//g'`; do
		    echo $i _hwtype=\$hwtypenum >> ${D}/${sysconfdir}/modules-load.d/_${MACHINE}.conf
		done
}
