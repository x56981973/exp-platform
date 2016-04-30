<!DOCTYPE html>
<html lang="en">
<head>
<#include "../../common.ftl">
    <!-- start: Meta -->
    <meta charset="utf-8">
    <title>无线攻防实验平台</title>
    <meta name="description" content="">
    <meta name="author" content="">
    <meta name="keyword" content="">
    <!-- end: Meta -->

    <!-- start: Mobile Specific -->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- end: Mobile Specific -->

    <!-- start: CSS -->
    <link id="bootstrap-style" href="${base}/main/css/bootstrap.min.css" rel="stylesheet">

    <link href="${base}/main/css/bootstrap-responsive.min.css" rel="stylesheet">

    <link id="base-style" href="${base}/main/css/style.css" rel="stylesheet">

    <link id="base-style-responsive" href="${base}/main/css/style-responsive.css" rel="stylesheet">

    <link href="//cdn.bootcss.com/toastr.js/2.1.2/toastr.min.css" rel="stylesheet">

    <link rel="stylesheet" type="text/css" href="${base}/main/css/sweetalert.css">

    <link href='http://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,600,700,800&subset=latin,cyrillic-ext,latin-ext' rel='stylesheet' type='text/css'>
    <!-- end: CSS -->


    <!-- The HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
    <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <link id="ie-style" href="${base}/main/css/ie.css" rel="stylesheet">
    <![endif]-->

    <!--[if IE 9]>
    <link id="ie9style" href="${base}/main/css/ie9.css" rel="stylesheet">
    <![endif]-->

    <!-- start: Favicon -->
<#--<link rel="shortcut icon" href="img/favicon.ico">-->
    <!-- end: Favicon -->

</head>

<body>
<!-- start: Header -->
<div class="navbar">
    <div class="navbar-inner">
        <div class="container-fluid">
            <a class="btn btn-navbar" data-toggle="collapse" data-target=".top-nav.nav-collapse,.sidebar-nav.nav-collapse">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </a>
            <a class="brand" href="${base}/"><span>无线攻防实验平台 后台管理</span></a>

            <!-- start: Header Menu -->
            <div class="nav-no-collapse header-nav">
                <ul class="nav pull-right">
                    <li class="dropdown hidden-phone">
                        <a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
                            <i class="halflings-icon white tasks"></i>
                        </a>
                        <ul class="dropdown-menu tasks">
                            <li class="dropdown-menu-title">
                                <span>You have 17 tasks in progress</span>
                                <a href="#refresh"><i class="icon-repeat"></i></a>
                            </li>
                            <li>
                                <a href="#">
										<span class="header">
											<span class="title">iOS Development</span>
											<span class="percent"></span>
										</span>
                                    <div class="taskProgress progressSlim red">80</div>
                                </a>
                            </li>
                        </ul>
                    </li>
                    <!-- end: Notifications Dropdown -->

                    <li>
                        <a class="btn" href="${base}/setting">
                            <i class="halflings-icon white wrench"></i>
                        </a>
                    </li>

                    <!-- start: User Dropdown -->
                    <li class="dropdown">
                        <a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
                            <i class="halflings-icon white user"></i> ${t_name}
                            <span class="caret"></span>
                        </a>
                        <ul class="dropdown-menu">
                            <li class="dropdown-menu-title">
                                <span>账户信息</span>
                            </li>
                            <li><a href="${base}/"><i class="halflings-icon user"></i> 账户概览</a></li>
                            <li><a href="${base}/logout"><i class="halflings-icon off"></i> 退出账户</a></li>
                        </ul>
                    </li>
                    <!-- end: User Dropdown -->
                </ul>
            </div>
            <!-- end: Header Menu -->
        </div>
    </div>
</div>