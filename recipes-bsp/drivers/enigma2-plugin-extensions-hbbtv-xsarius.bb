DESCRIPTION = "enigma2-plugin-extensions-hbbtv"
LICENSE = "CLOSED"
PACKAGE_ARCH := "${MACHINE_ARCH}"
SRCREV = "${AUTOREV}"
PR = "r1"

SRC_URI = "git://github.com/pli3/enigma2-plugin-extensions-hbbtv-xsarius.git"

EXTRA_OECONF = " \
    BUILD_SYS=${BUILD_SYS} \
    HOST_SYS=${HOST_SYS} \
    STAGING_INCDIR=${STAGING_INCDIR} \
    STAGING_LIBDIR=${STAGING_LIBDIR} \
"

ALLOW_EMPTY_${PN} = "1"

S = "${WORKDIR}/git"
DEPLOY_DIR = "${TMPDIR}/deploy"

ENIGMA2_PLUGIN_EXTENSIONS_HBBTV = " \
	enigma2-plugin-extensions-hbbtv-xsarius_git8507+077f312-r0_mips32el.ipk \
"

do_install() {
}
python populate_packages_prepend () {
    p = ""
    plugins = d.getVar('ENIGMA2_PLUGIN_EXTENSIONS_HBBTV', True)

    if plugins is not None:
        for package in plugins.split():
            p += package.split('_')[0] + " "

    d.setVar('PACKAGES', p)
}

do_deploy() {
    install -d -m 0755 ${WORKDIR}/deploy-ipk/mips32el

    for i in ${ENIGMA2_PLUGIN_EXTENSIONS_HBBTV}; do
        if [ -f $i ]; then
            install -m 0644 $i ${WORKDIR}/deploy-ipk/mips32el;
            install -m 0644 $i ${DEPLOY_DIR}/ipk/mips32el;
        fi
    done;

    pkgdir=${DEPLOY_DIR_IPK}/mips32el
    if [ -e $pkgdir ]; then
        chmod 0755 $pkgdir
    fi
}

addtask do_deploy before do_package_write after do_package_write_ipk

