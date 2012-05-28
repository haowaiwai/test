<table border="0" align="center">
  <tr>
    <td><a href="http://www.nanmadeit.com"><img src="http://www.nanmadeit.com/img/logo.png" hspace="0" vspace="0" border="0"/></a></td>
  </tr>
</table>
<table id="t1" border="0" cellpadding="0" cellspacing="0" align="center" width="200">
<tr>
<td><a href="http://www.nanmadeit.com/home.php"><img src="http://www.nanmadeit.com/img/a01.jpg" border="0" /></a></img></td>
<td><a href="http://www.nanmadeit.com/products/products_list.php"><img src="http://www.nanmadeit.com/img/b01.jpg" border="0"></a></img></td>
<td><a href="http://nanmadeit.taobao.com/"><img src="http://www.nanmadeit.com/img/c01.jpg" border="0"></a></img></td>
<td><a href="http://www.nanmadeit.com/club/club.php"><img src="http://www.nanmadeit.com/img/d01.jpg" border="0"></a></img></td>
<td><a href="http://www.nanmadeit.com/about/about.php"><img src="http://www.nanmadeit.com/img/e01.jpg"border="0"></a></img></td>
</tr>
</table>
<script type="text/javascript">
$(document).ready(function (){
	$("#t1 img").bind("mouseover",function(){
			var src=$(this).attr("src");
			$(this).attr("src",src.replace("01","02"));
		}).bind("mouseout",function(){
			var src=$(this).attr("src");
			$(this).attr("src",src.replace("02","01"));
		});	
})
</script>