var gulp = require('gulp');
var del = require('del');
var plumber = require('gulp-plumber');
var rename = require('gulp-rename');
var tsc = require('gulp-typescript');
var sourcemaps = require('gulp-sourcemaps');
var rework = require('rework');
var path = require('path');
var fs = require('fs');
var mkpath = require('mkpath');
var browserSync = require('browser-sync');
var spa = require('browser-sync-spa');

const src = {
    ts: 'src/**/*.ts',
    html: 'src/**/*.html',
    libs: [
        "./node_modules/angular2/bundles/angular2-polyfills.js",
        "./node_modules/angular2/bundles/router.dev.js",
        "./node_modules/es6-shim/es6-shim.min.js",
        "./node_modules/systemjs/dist/system.js",
        "./node_modules/rxjs/bundles/Rx.js",
        "./node_modules/angular2/bundles/angular2.min.js"
    ],
    assets: {
        css: 'src/assets/css/*.css',
        img: 'src/assets/img/*.png'
    }
};
const dist = "dist";

const browserSyncInit = function (baseDir) {
    browserSync.instance = browserSync.init({
        reloadDelay: 500,
        startPath: '/',
        open: true,
        server: {
            baseDir: baseDir
        },
        notify: true
    });
};
browserSync.use(spa({
    selector: "[book-app]"
}));

gulp.task('clean', function (done) {
    var del = require('del');
    del([dist], done);
});

gulp.task('copy-files', function () {
    gulp.src(src.assets.css, {base: './src/'}).pipe(gulp.dest(dist));
    gulp.src(src.assets.img, {base: './src/'}).pipe(gulp.dest(dist));
    gulp.src(src.html, {base: './src/'}).pipe(gulp.dest(dist));
    return gulp.src(src.libs, {base: './node-modules/'})
        .pipe(gulp.dest(dist + '/node_modules/'))
        .pipe(browserSync.reload({stream: true}));
});

gulp.task('ts2js', function () {
    var typescript = require('gulp-typescript');
    var tscConfig = require('./tsconfig.json');

    var tsResult = gulp
        .src(src.ts)
        .pipe(typescript(tscConfig.compilerOptions));

    return tsResult.js.pipe(gulp.dest(dist))
        .pipe(browserSync.reload({stream: true}));
});
gulp.task('watch', function() {
    gulp.watch(src.ts, ['ts2js']);
    gulp.watch(src.html, ['copy-files']);
    gulp.watch(src.assets.css, ['copy-files']);
})
;
gulp.task('build', ['ts2js', 'copy-files']);
gulp.task('serve', ['build', 'watch'], function () {
    browserSyncInit(dist);
});

gulp.task('default', ['serve']);
