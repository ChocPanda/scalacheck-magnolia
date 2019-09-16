addSbtPlugin("com.geirsson"      % "sbt-ci-release"         % "1.3.2")
addSbtPlugin("com.dwijnand"      % "sbt-travisci"           % "1.2.0")
addSbtPlugin("org.scalameta"     % "sbt-scalafmt"           % "2.0.1")
addSbtPlugin("de.heikoseeberger" % "sbt-header"             % "5.2.0")
addSbtPlugin("org.wartremover"   % "sbt-wartremover"        % "2.4.2")
addSbtPlugin("ch.epfl.scala"     % "sbt-scalafix"           % "0.9.7")
addSbtPlugin("org.scalastyle"    %% "scalastyle-sbt-plugin" % "1.0.0")
addSbtPlugin("net.virtual-void"  % "sbt-dependency-graph"   % "0.9.2")
addSbtPlugin("net.vonbuchholtz"  % "sbt-dependency-check"   % "1.3.0")
addSbtPlugin("com.eed3si9n"      % "sbt-buildinfo"          % "0.9.0")
addSbtPlugin("org.scoverage"     % "sbt-scoverage"          % "1.6.0")
addSbtPlugin("com.codacy"        % "sbt-codacy-coverage"    % "3.0.2")

libraryDependencies += "org.slf4j" % "slf4j-nop" % "1.7.28" // Needed by sbt-git
