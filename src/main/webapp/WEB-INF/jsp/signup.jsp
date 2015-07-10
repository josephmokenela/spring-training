<%@include file="includes/header.jsp" %>
    
   <div class="panel panel-default">
      
      <div class="panel-heading">
      	<h3 class="panel-title">Please sign up</h3>
      </div>
      
      <div class="panel-body">
      	<form:form modelAttribute="signupform" role="form">
      	
      	    <form:errors />
      		
      		<div class="form-group">
      			<form:label path="email">Email Address</form:label>
      			<form:input path="email" type="email" class="form-control" placeholder="Email"/>
      			<form:errors cssClass="error" path="email" />
      			<p class="help-block">Enter your unique email address, It will also be your username</p>
      		</div>
      		
      		<div class="form-group">
      			<form:label path="name">Name</form:label>
      			<form:input path="name" class="form-control" placeholder="Name"/>
      			<form:errors cssClass="error" path="name" />
      			<p class="help-block">Enter your unique name</p>
      		</div>
      		
      		<div class="form-group">
      			<form:label path="password">Email Address</form:label>
      			<form:password path="password" class="form-control" placeholder="Password"/>
      			<form:errors cssClass="error" path="password" />
      			<p class="help-block">Enter your unique password</p>
      		</div>
      		
      		
      		<button type="submit" class="btn btn-primary">Submit</button>
      		
      	</form:form>
      </div>
      
   </div>

 <%@include file="includes/footer.jsp" %>