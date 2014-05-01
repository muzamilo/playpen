<?PHP

require($include.'head.inc');

echo "
	<body id='login'>
		<!-- possible classes: minified, no-right-panel, fixed-ribbon, fixed-header, fixed-width-->
		<header id='header'>
			<!--<span id='logo'></span>-->

			<div id='logo-group'>
				<span id='logo'> <img src='img/logo.png' alt='SmartAdmin'> </span>

				<!-- END AJAX-DROPDOWN -->
			</div>

			<span id='login-header-space'> <span class='hidden-mobile'>Already registered?</span> <a href='login.php' class='btn btn-danger'>Sign In</a> </span>

		</header>

		<div id='main' role='main'>

			<!-- MAIN CONTENT -->
			<div id='content' class='container'>

				<div class='row'>
					<div class='col-xs-12 col-sm-12 col-md-7 col-lg-7 hidden-xs hidden-sm'>
						<h1 class='txt-color-red login-header-big'>BASSAT</h1>
						<div class='hero'>

							<div class='pull-left login-desc-box-l'>
								<h4 class='paragraph-header'>Bank Account Statement Statistical Aggregation Tool</h4>
								<div class='login-app-icons'>
									<a href='javascript:void(0);' class='btn btn-danger btn-sm'>Find out more</a>
								</div>
							</div>

							<img src='img/login.jpg' alt='' class='pull-left display-image' style='width:300px'>

						</div>

						<div class='row'>
							<div class='col-xs-12 col-sm-12 col-md-6 col-lg-6'>
								<h5 class='about-heading'>About Bassat - Are you up to date?</h5>
								<p>
									Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa.
								</p>
							</div>
							<div class='col-xs-12 col-sm-12 col-md-6 col-lg-6'>
								<h5 class='about-heading'>Not just your average template!</h5>
								<p>
									Et harum quidem rerum facilis est et expedita distinctio. Nam libero tempore, cum soluta nobis est eligendi voluptatem accusantium!
								</p>
							</div>
				        </div>
					</div>
					<div class='col-xs-12 col-sm-12 col-md-5 col-lg-5'>
						<div class='well no-padding'>

							<form action='register.php' id='register' class='smart-form client-form'>
								<header>
									Registration is FREE*
								</header>

								<fieldset>
									<section>
										<label class='input'> <i class='icon-append fa fa-envelope'></i>
											<input type='email' name='email' placeholder='Email address'>
											<b class='tooltip tooltip-bottom-right'>Needed to verify your account</b> </label>
									</section>

									<section>
										<label class='input'> <i class='icon-append fa fa-lock'></i>
											<input type='password' name='password' placeholder='Password' id='password'>
											<b class='tooltip tooltip-bottom-right'>Don't forget your password</b> </label>
									</section>

									<section>
										<label class='input'> <i class='icon-append fa fa-lock'></i>
											<input type='password' name='passwordConfirm' placeholder='Confirm password'>
											<b class='tooltip tooltip-bottom-right'>Don't forget your password</b> </label>
									</section>
								</fieldset>

								<fieldset>
									<div class='row'>
										<section class='col col-6'>
											<label class='input'>
												<input type='text' name='idNumber' placeholder='ID number'>
											</label>
										</section>
									</div>

									<div class='row'>
										<section class='col col-6'>
											<label class='select'>
												<select name='title'>
													<option value='' selected='' disabled=''>Title</option>
													<option value='Mr'>Mr</option>
													<option value='Mrs'>Mrs</option>
													<option value='Miss'>Miss</option>
													<option value='Ms'>Ms</option>
													<option value='Dr'>Dr</option>
													<option value='Prof'>Prof</option>
												</select> <i></i> </label>
										</section>
									</div>

									<div class='row'>
										<section class='col col-6'>
											<label class='input'>
												<input type='text' name='firstname' placeholder='First name'>
											</label>
										</section>
										<section class='col col-6'>
											<label class='input'>
												<input type='text' name='lastname' placeholder='Last name'>
											</label>
										</section>
									</div>

									<section>
										<label class='checkbox'>
											<input type='checkbox' name='terms' id='terms'>
											<i></i>I agree with the Terms and Conditions - <a href='#' data-toggle='modal' data-target='#myModal'>Click to View</a></label>
									</section>
								</fieldset>
								<footer>
									<button type='submit' class='btn btn-primary' name='register'>
										Register
									</button>
								</footer>

								<div class='message'>
									<i class='fa fa-check'></i>
									<p>
										Thank you for your registration!
									</p>
								</div>
							</form>

						</div>
					</div>
				</div>
			</div>

		</div>

		<!-- Modal -->
		<div class='modal fade' id='myModal' tabindex='-1' role='dialog' aria-labelledby='myModalLabel' aria-hidden='true'>
			<div class='modal-dialog'>
				<div class='modal-content'>
					<div class='modal-header'>
						<button type='button' class='close' data-dismiss='modal' aria-hidden='true'>
							&times;
						</button>
						<h4 class='modal-title' id='myModalLabel'>Terms & Conditions</h4>
					</div>
					<div class='modal-body custom-scroll terms-body'>";

						include $include.'terms_and_conditions.html';

						echo "
                        <br><br>

                                <p><strong>By using this  WEBSITE TERMS AND CONDITIONS template document, you agree to the
                         <a href='#'>terms and conditions</a> set out on
                         <a href='#'>SmartAdmin.com</a>.  You must retain the credit
                         set out in the section headed 'ABOUT THESE WEBSITE TERMS AND CONDITIONS'.  Subject to the licensing restrictions, you should
                         edit the document, adapting it to the requirements of your jurisdiction, your business and your
                         website.  If you are not a lawyer, we recommend that you take professional legal advice in relation to the editing and
                         use of the template.</strong></p>

					</div>
					<div class='modal-footer'>
						<button type='button' class='btn btn-default' data-dismiss='modal'>
							Close
						</button>
						<button type='button' class='btn btn-danger pull-left' id='print'>
							<i class='fa fa-print'></i> Print
						</button>
					</div>
				</div><!-- /.modal-content -->
			</div><!-- /.modal-dialog -->
		</div><!-- /.modal -->

		<!--================================================== -->";

        include $include.'full_js_dependencies.html';

        echo "
		<script type='text/javascript'>
			runAllForms();

			// Validation
			$(function() {
				// Validation
				$('#register').validate({

					// Rules for form validation
					rules : {
						email : {
							required : true,
							minlength : 5,
							maxlength : 50,
							email : true
						},
						password : {
							required : true,
							minlength : 3,
							maxlength : 20
						},
						passwordConfirm : {
							required : true,
							minlength : 3,
							maxlength : 20,
							equalTo : '#password'
						},
						firstname : {
							required : false,
							minlength : 2,
							maxlength : 30
						},
						lastname : {
							required : false,
							minlength : 2,
							maxlength : 50
						},
						title : {
							required : false
						},
						idNumber : {
							required : true,
							minlength : 13,
							maxlength : 13
						},
						terms : {
							required : true
						}
					},

					// Messages for form validation
					messages : {
						login : {
							required : 'Please enter your login'
						},
						email : {
							required : 'Please enter your email address',
							email : 'Please enter a VALID email address'
						},
						password : {
							required : 'Please enter your password'
						},
						passwordConfirm : {
							required : 'Please enter your password one more time',
							equalTo : 'Please enter the same password as above'
						},
						idNumber : {
							required : 'Please enter a valid South African ID number'
						},
						terms : {
							required : 'You must agree with Terms and Conditions'
						}
					},

					// Do not change code below
					errorPlacement : function(error, element) {
						error.insertAfter(element.parent());
					}
				});

			});
		</script>
	</body>
";

require($include.'footer.inc');

?>