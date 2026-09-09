<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:url value="/" var="URL" />

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <title><sitemesh:write property="title"/></title>
  <meta content="width=device-width, initial-scale=1.0" name="viewport">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">

  <!-- Fonts START -->
  <link href="http://fonts.googleapis.com/css?family=Open+Sans:300,400,600,700|PT+Sans+Narrow|Source+Sans+Pro:200,300,400,600,700,900&amp;subset=all" rel="stylesheet" type="text/css">
  <!-- Fonts END -->

  <!-- Global styles START -->
  <link href="${URL}assets/global/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet">
  <link href="${URL}assets/global/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet">
  <!-- Global styles END --> 

  <!-- Page level plugin styles START -->
  <link href="${URL}assets/global/plugins/fancybox/source/jquery.fancybox.css" rel="stylesheet">
  <link href="${URL}assets/global/plugins/carousel-owl-carousel/owl-carousel/owl.carousel.css" rel="stylesheet">
  <link href="${URL}assets/global/plugins/slider-layer-slider/css/layerslider.css" rel="stylesheet">
  <!-- Page level plugin styles END -->

  <!-- Theme styles START -->
  <link href="${URL}assets/global/css/components.css" rel="stylesheet">
  <link href="${URL}assets/frontend/layout/css/style.css" rel="stylesheet">
  <link href="${URL}assets/frontend/pages/css/style-shop.css" rel="stylesheet" type="text/css">
  <link href="${URL}assets/frontend/pages/css/style-layer-slider.css" rel="stylesheet">
  <link href="${URL}assets/frontend/layout/css/style-responsive.css" rel="stylesheet">
  <link href="${URL}assets/frontend/layout/css/themes/red.css" rel="stylesheet" id="style-color">
  <link href="${URL}assets/frontend/layout/css/custom.css" rel="stylesheet">
  <!-- Theme styles END -->

  <sitemesh:write property="head"/>
</head>

<body class="ecommerce">
  <%@ include file="/commons/web/header.jsp" %>

  <div class="main">
    <div class="container">
      <sitemesh:write property="body"/>
    </div>
  </div>

  <%@ include file="/commons/web/footer.jsp" %>

  <script src="${URL}assets/global/plugins/jquery.min.js" type="text/javascript"></script>
  <script src="${URL}assets/global/plugins/jquery-migrate.min.js" type="text/javascript"></script>
  <script src="${URL}assets/global/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>      
  <script src="${URL}assets/frontend/layout/scripts/back-to-top.js" type="text/javascript"></script>
  <script src="${URL}assets/global/plugins/jquery-slimscroll/jquery.slimscroll.min.js" type="text/javascript"></script>
  <script src="${URL}assets/global/plugins/fancybox/source/jquery.fancybox.pack.js" type="text/javascript"></script>
  <script src="${URL}assets/global/plugins/carousel-owl-carousel/owl-carousel/owl.carousel.min.js" type="text/javascript"></script>
  <script src='${URL}assets/global/plugins/zoom/jquery.zoom.min.js' type="text/javascript"></script>
  <script src="${URL}assets/global/plugins/bootstrap-touchspin/bootstrap.touchspin.js" type="text/javascript"></script>
  <script src="${URL}assets/frontend/layout/scripts/layout.js" type="text/javascript"></script>
  <script type="text/javascript">
      jQuery(document).ready(function() {
          Layout.init();    
          Layout.initOWL();
          Layout.initImageZoom();
          Layout.initTouchspin();
      });
  </script>
</body>
</html>