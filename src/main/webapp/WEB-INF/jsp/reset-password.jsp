<%@include file="includes/header.jsp" %>
    
    
    <div class="panel panel-primary">
    	
    	<div class="panel-heading">
    		<h3 class="panel-title">Forgot Password?</h3>
    	</div>
    	
    	<div class="panel-body">
    		
    		<form:form modelAttribute="resetPasswordForm" role="form">
    			
    			<form:errors />
    			
    			<div class="form-group">
    				<form:label path="password">Password</form:label>
    				<form:password path="password"  class="form-control" placeholder="Please enter your password"/>
    				<form:errors cssClass="error" path="password" />
    				<p class="help-block">Please enter your Password</p>
    			</div>
    			
    			<div class="form-group">
    				<form:label path="retypePassword">Retype Password</form:label>
    				<form:password path="retypePassword"  class="form-control" placeholder="Please retype your password"/>
    				<form:errors cssClass="error" path="retypePassword" />
    				<p class="help-block">Please retype your password to confirm</p>
    			</div>
    			
    			<button type="submit" class="btn btn-default">Reset Password</button>
    		</form:form>
    	</div>
    </div>
    
 <%@include file="includes/footer.jsp" %>