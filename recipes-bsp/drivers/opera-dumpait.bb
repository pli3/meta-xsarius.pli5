DESCRIPTION = "opera browser util"
LICENSE = "CLOSED"

SRCREV = "${AUTOREV}"
PR = "r1"

inherit gitpkgv deploy

SRC_URI="git://github.com/MOA-2011/vuplus-opera-dumpait.git"

EXTRA_OECONF = " \
			 BUILD_SYS=${BUILD_SYS} \
			 HOST_SYS=${HOST_SYS} \
			 STAGING_INCDIR=${STAGING_INCDIR} \
			 STAGING_LIBDIR=${STAGING_LIBDIR} \
"

ALLOW_EMPTY_${PN} = "1"

S = "${WORKDIR}/git"

DEPLOY_DIR = "${TMPDIR}/deploy"

OPERA_DUMPAIT = "opera-dumpait_git11+c0616d5-r10_mips32el.ipk"

do_install() {
}

python populate_packages_prepend () {
    p = ""
    plugins = d.getVar('OPERA_BROWSER', True)

    if plugins is not None:
        for package in plugins.split():
            p += package.split('_')[0] + " "

    d.setVar('PACKAGES', p)
}

do_deploy() {
    install -d -m 0755 ${WORKDIR}/deploy-ipk/mips32el

    for i in ${S}/${OPERA_DUMPAIT}; do
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
