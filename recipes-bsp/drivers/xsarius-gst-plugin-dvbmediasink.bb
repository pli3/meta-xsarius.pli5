SUMMARY = "Plugin for gstreamer: dvbmediasink"
SECTION = "multimedia"
LICENSE = "MIT | LGPLv2.1"
LIC_FILES_CHKSUM = "file://src/gstdvbaudiosink.c;md5=b014da5e2d58d4840e0ef33911b0191f\
                    file://src/gstdvbvideosink.c;md5=fa4eeb1987eaea718ed8c5e974e22d14"

DEPENDS = "gstreamer gst-plugins-base"
RDEPENDS_${PN} = "gst-ffmpeg"

PR = "r12"
SRCREV = "${AUTOREV}"

EXTRA_OECONF = "--with-wmv --with-pcm --with-dts"

inherit autotools git-project pkgconfig

do_configure_prepend() {
    touch ${S}/NEWS
    touch ${S}/README
}

BRANCH="master"
SRC_URI = "git://github.com/MOA-2011/xsarius-gst-plugin-mediasink.git;protocol=git;branch=${BRANCH}"

FILES_${PN} = "${libdir}/gstreamer-0.10/*.so"
FILES_${PN}-dev += "${libdir}/gstreamer-0.10/*.la"
FILES_${PN}-staticdev += "${libdir}/gstreamer-0.10/*.a"
FILES_${PN}-dbg += "${libdir}/gstreamer-0.10/.debug"
