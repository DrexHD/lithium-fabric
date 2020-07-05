![Project icon](https://git-assets.jellysquid.me/hotlink-ok/lithium/icon-rounded-128px.png)

![Project icon](https://github.com/jellysquid3/lithium-fabric/raw/1.16.x/dev/doc/logo.png)

# Note
This fork is a private version I maintain for my snapshot server (Kilocraft), use at your own risk!

# Lithium (for Fabric)
![GitHub license](https://img.shields.io/github/license/jellysquid3/lithium-fabric.svg)
![GitHub issues](https://img.shields.io/github/issues/jellysquid3/lithium-fabric.svg)
![GitHub tag](https://img.shields.io/github/tag/jellysquid3/lithium-fabric.svg)
[![Discord chat](https://img.shields.io/badge/chat%20on-discord-7289DA)](https://jellysquid.me/discord)
[![CurseForge downloads](http://cf.way2muchnoise.eu/full_360438_downloads.svg)](https://www.curseforge.com/minecraft/mc-mods/lithium)

Lithium is a free and open-source Minecraft mod which works to optimize many areas of the game in order to provide
better overall performance. It works on both the **client and server**, and **doesn't require the mod to be installed
on both sides**.

### Downloads

You can find downloads for Lithium on either the [official CurseForge page](https://www.curseforge.com/minecraft/mc-mods/lithium)
or through the [GitHub releases page](https://github.com/jellysquid3/lithium-fabric/releases). Usually, builds will be
made available on GitHub slightly sooner than other locations.

### Community

If you'd like to get help with the mod, check out the latest developments, or be notified when there's a new release,
the Discord community might be for you! You can join the official server for my mods by clicking
[here](https://jellysquid.me).

---

### What makes Lithium different?

One of the most important design goals in Lithium is *correctness*. Unlike other mods which apply optimizations to the
game, Lithium does not sacrifice vanilla functionality or behavior in the name of raw speed. It's a no compromises'
solution for those wanting to speed up their game, and as such, installing Lithium should be completely transparent
to the player.

If you do encounter an issue where Lithium deviates from the norm, please don't hesitate to
[open an issue.](https://github.com/jellysquid3/lithium-fabric/issues) Each patch is carefully checked to ensure
vanilla parity, but after all, bugs are unavoidable.

### Configuration

Out of the box, no additional configuration is necessary once the mod has been installed. Lithium makes use of a
configuration override system which allows you to either forcefully disable problematic patches or enable incubating
patches which are otherwise disabled by default. As such, an empty config file simply means you'd like to use the
default configuration, which includes all stable optimizations by default.

See [the Wiki page](https://github.com/jellysquid3/lithium-fabric/wiki/Configuration-File) on the configuration file
format and all available options.

---

<a href="https://www.patreon.com/bePatron?u=824442"><img src="https://raw.githubusercontent.com/jellysquid3/phosphor-forge/1.15.x/dev/doc/patreon.png" width="200"></a>

If you're hacking on the code or would like to compile a custom build of Lithium from the latest sources, you'll want
to start here.

#### Prerequisites

You will need to install JDK 8 in order to build Lithium. You can either install this through a package manager such as
[Chocolatey](https://chocolatey.org/) on Windows or [SDKMAN!](https://sdkman.io/) on other platforms. If you'd prefer to
not use a package manager, you can always grab the installers or packages directly from
[AdoptOpenJDK](https://adoptopenjdk.net/).

On Windows, the Oracle JDK/JRE builds should be avoided where possible due to their poor quality. Always prefer using
the open-source builds from AdoptOpenJDK when possible.

#### Compiling

Navigate to the directory you've cloned this repository and launch a build with Gradle using `gradlew build` (Windows)
or `./gradlew build` (macOS/Linux). If you are not using the Gradle wrapper, simply replace `gradlew` with `gradle`
or the path to it.

The initial setup may take a few minutes. After Gradle has finished building everything, you can find the resulting
artifacts in `build/libs`.

---

### License

Lithium is licensed under GNU LGPLv3, a free and open-source license. For more information, please see the
[license file](https://github.com/jellysquid3/lithium-fabric/blob/1.16.x/fabric/LICENSE.txt).
