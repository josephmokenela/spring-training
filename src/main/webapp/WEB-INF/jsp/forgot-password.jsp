<%@include file="includes/header.jsp" %>
    
    
    <div class="panel panel-primary">
    	
    	<div class="panel-heading">
    		<h3 class="panel-title">Forgot Password?</h3>
    	</div>
    	
    	<div class="panel-body">
    		
    		<form:form modelAttribute="forgotPasswordForm" role="form">
    			
    			<form:errors />
    			
    			<div class="form-group">
    				<form:label path="email">Email Address</form:label>
    				<form:input path="email" type="email" class="form-control" placeholder="Please enter your email"/>
    				<form:errors cssClass="error" path="email" />
    				<p class="help-block">Please enter your email id</p>
    			</div>
    			
    			<button type="submit" class="btn btn-default">Reset Password</button>
    		</form:form>
    	</div>
    </div>
    
 <%@include file="includes/footer.jsp" %>