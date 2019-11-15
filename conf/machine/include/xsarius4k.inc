# We know about rtc
MACHINE_FEATURES_BACKFILL_CONSIDERED = "rtc"

MTD_KERNEL = "mmcblk0p1"
TARGET_ARCH = "arm"
DEFAULTTUNE = "armv7ahf-neon"
MACHINE_ESSENTIAL_EXTRA_RDEPENDS = " \
	${@bb.utils.contains("GST_VERSION", "1.0", "gstreamer1.0-libav", "gst-ffmpeg", d)} \
        xsarius-initrd-${MACHINE} \
        xsarius-shutdown \
"

KERNEL_MODULE_AUTOLOAD += "xfs"

MACHINE_EXTRA_RRECOMMENDS = " \
	gstreamer1.0-plugin-dvbmediasink \
	ntfs-3g \
"

EXTRA_IMAGEDEPENDS += "\
	webkit-classic \
	dumpait-legacy \
	enigma2-plugin-extensions-hbbtv-webkit \
        splash-bootlogo \
"

DVBMEDIASINK_CONFIG = "--with-pcm --with-eac3 --with-amr --with-wmv --with-h265 --with-vb6 --with-vb8 --with-vb9 --with-spark"

PREFERRED_PROVIDER_virtual/kernel = "linux-xsarius"

IMAGE_FSTYPES ?= "tar.bz2"

PREFERRED_VERSION_directfb = "1.7.7"

IMAGEDIR ?= "${MACHINE}"

IMAGE_CMD_tar_prepend = " \
	mkdir -p ${DEPLOY_DIR_IMAGE}/update/${UPDATE_DIR}/; \
	cp -f ${DEPLOY_DIR_IMAGE}/${MACHINE}_splash.bmp ${DEPLOY_DIR_IMAGE}/update/${UPDATE_DIR}/splash.bmp; \
        cp ${IMAGE_ROOTFS}/tmp/zImage ${DEPLOY_DIR_IMAGE}/update/${MACHINE}/oe_kernel.bin; \
        cp ${IMAGE_ROOTFS}/boot/initrd_run.bin ${DEPLOY_DIR_IMAGE}/update/${UPDATE_DIR}/initrd_run.bin; \
        rm -rf ${IMAGE_ROOTFS}/tmp/zImage; \
        rm -rf ${IMAGE_ROOTFS}/boot/initrd_run.bin; \
        tar -cvf ${DEPLOY_DIR_IMAGE}/rootfs.tar -C ${IMAGE_ROOTFS} .; \
        mv ${DEPLOY_DIR_IMAGE}/rootfs.tar ${DEPLOY_DIR_IMAGE}/update/${UPDATE_DIR}/rootfs.tar; \
        bzip2 ${DEPLOY_DIR_IMAGE}/update/${MACHINE}/rootfs.tar; \
        cd ${DEPLOY_DIR_IMAGE}; \
        zip ${IMAGE_NAME}_usb.zip update/${UPDATE_DIR}/*; \
        rm -rf update; \
"

MACHINE_FEATURES += "alsa usbhost wlan kernelwifi extrakernelwifi 3dtv switchoff osdposition hdmicec"

require conf/machine/include/arm/arch-armv7a.inc
require conf/machine/include/xsarius-wifi.inc
require conf/machine/include/xsarius-dvb-usb.inc
