
'use strict';

$(document).ready(function () {

    // set logger position
    alertify.logPosition("bottom right");

    $('.toggle-dash-menu').on('click', function () {
        if(window.innerWidth < 760){
            $('.dash-side-nav-wrapper').toggleClass('slide-in');
        }else{
            $('.dash-side-nav-wrapper').toggleClass('slide-out');
            $('.top-nav').toggleClass('full-width');
            $('.content-wrapper').toggleClass('full-width');
        }
    });

    //hide side nav
    var sideMenu = document.getElementById('dash-side-menu'),
        replyBox = document.getElementById('ux-box'),
        siteMenu = document.getElementById('side-nav'),
        searchBox = document.getElementById('dash-search-box');
    window.onclick = function(event) {
        if (event.target === sideMenu) {
            $('.dash-side-nav-wrapper').toggleClass('slide-in');
        }else if (event.target === searchBox) {
            $('.dash-search-box').removeClass('show-search');
        }else if(event.target === siteMenu){
            $('.side-nav').removeClass('show-side-menu');
            $('body').removeClass('no-scroll');
        }else if(event.target === replyBox){
            $('.ux-box-wrapper').removeClass('show-ux-box');
        }
    };

    $('.show-search').on('click', function (e) {
       $('.dash-search-box').addClass('show-search');
        e.preventDefault();
    });
    $('.nav-toggle').on('click', function () {
        $('body').addClass('no-scroll');
        $('.side-nav').addClass('show-side-menu');
    });

    // delete review
    $('.confirm-action').on('click', function (e) {
        var _myThis = $(this);
        // confirm dialog
        alertify.confirm("Are you Sure?", function () {
            // user clicked "ok"
            // some logic here to talk to the server
            _myThis.closest('.review-item').fadeOut();
            alertify.success("Review Deleted Successfully");
        }, function() {
            // user clicked "cancel"
            alertify.error("You've clicked Cancel")
        });
        e.preventDefault();
    });

    // delete dashboard item
    $('.delete-item').on('click', function (e) {
        var _myThis = $(this);
        // confirm dialog
        alertify.confirm("Are you Sure?", function () {
            // user clicked "ok"
            // some logic here to talk to the server
            _myThis.closest('.product-item').fadeOut();
            alertify.success("Item Deleted Successfully");
        }, function() {
            // user clicked "cancel"
            alertify.error("You've clicked Cancel")
        });
        e.preventDefault();
    });

    $('.show-box').on('click', function (e) {
       $('.ux-box-wrapper').addClass('show-ux-box');
        e.preventDefault();
    });
    $('.close-box').on('click', function (e) {
        $('.ux-box-wrapper').removeClass('show-ux-box');
        e.preventDefault();
    });


    //chat box js
    var chatsContainer = $('.chat-flow');
    $('.chat-box-btn').on('click', function (e) {
       $('.chat-box').toggleClass('show-chat');
       $('.chat-top section').toggleClass('slide-s');
       $('.icon-sw').toggleClass('fa-comment fa-times');
        e.preventDefault();
    });


    $('.chat-form').on('submit', function (e) {
        var chatInput = $(this).find('.chat-message');
        if(chatInput.val() !== ''){
            chatsContainer.append('<p class="outbound">' + chatInput.val() + '</p>')
                .perfectScrollbar('update')
                .animate({
                    scrollTop: chatsContainer.height() + 20
                }, 500);
            chatInput.val('');
            //some logic here to communicate with the server
        }
        e.preventDefault();
    });
    chatsContainer.perfectScrollbar();

    //side nav menu
    $('.perfect-scroll').perfectScrollbar();
    $(window).on('resize', function () {
        $('.perfect-scroll').perfectScrollbar('update');
    });


    //----------cart js
    //remove item from cart
    $('.remove-item').on('click', function () {
        $(this).closest('tr').fadeOut().remove();
        $('.sub-total').text(total());
    });

    //increase/decrease item quantity buttons
    $('.add-item').on('click', function () {
        var input = $(this).parent().find('.quantity-input');
        input.val( function(i, oldval) {
            return ++oldval;
        }).change();
        $('.sub-total').text(total());
    });
    $('.minus-item').on('click', function () {
        var input = $(this).parent().find('.quantity-input');
        if(input.val() > 0){
            input.val( function(i, oldval) {
                return --oldval;
            }).change();
        }
        $('.sub-total').text(total());
    });

    //calculate totals
    $('.quantity-input').on('change', function () {
        var unitPrice = $(this).closest('.price-control').find('.unit-price span').data('price');
        $(this).closest('tr').find('.total').text(parseFloat($(this).val()) * parseFloat(unitPrice))
    });

    function total() {
        var totalPrice = 0;

        $.each($('.total'), function (index, value) {
            totalPrice += parseFloat(value.textContent);
        });
        return totalPrice
    }

    //switch auth forms
    var switchLink = $('.switch-nav a');
    switchLink.on('click', function (e) {
        var _this = $(this);
        switchLink.removeClass('current');
        _this.addClass('current');
        $('.forms-c form').hide();
        $('.'+_this.data('form')).show().addClass('fadeIn');
        e.preventDefault();
    });

    // show password
    $('.reveal-password').on('click', function () {
        $(this).toggleClass('fa-eye fa-eye-slash');
        var passInput = $(this).parent().find('.form-control');
        if($(this).hasClass('fa-eye-slash')){
            passInput.attr('type', 'text')
        }else{
            passInput.attr('type', 'password')
        }
    });

    //details page show more
    $('.show-more').on('click', function (e) {
        $('.description').toggleClass('max-height');
        $(this).find('i').toggleClass('fa-angle-double-down fa-angle-double-up');
        e.preventDefault();
    });

    //user image select
    window.URL = window.URL || window.webkitURL;
    var fileInput = document.getElementById('file'),
        imgElement = document.getElementById('user-photo');
    $('.img-file').on('change', function () {
        var imgFile = fileInput.files[0];
        if (imgFile.type.indexOf('image/') == 0){
            imgElement.src = window.URL.createObjectURL(imgFile);
            alertify.success("Photo Selected Successfully");
            imgElement.onload = function() {
                // release object url
                window.URL.revokeObjectURL(this.src);
            }
        }else{
            alertify.error("File not Image!");
        }
    });


    // jquery knob
    $(".dial").knob({
        draw : function () {

            // "tron" case
            if(this.$.data('skin') == 'tron') {

                this.cursorExt = 0.3;

                var a = this.arc(this.cv)  // Arc
                    , pa                   // Previous arc
                    , r = 1;

                this.g.lineWidth = this.lineWidth;

                if (this.o.displayPrevious) {
                    pa = this.arc(this.v);
                    this.g.beginPath();
                    this.g.strokeStyle = this.pColor;
                    this.g.arc(this.xy, this.xy, this.radius - this.lineWidth, pa.s, pa.e, pa.d);
                    this.g.stroke();
                }

                this.g.beginPath();
                this.g.strokeStyle = r ? this.o.fgColor : this.fgColor ;
                this.g.arc(this.xy, this.xy, this.radius - this.lineWidth, a.s, a.e, a.d);
                this.g.stroke();

                this.g.lineWidth = 2;
                this.g.beginPath();
                this.g.strokeStyle = this.o.fgColor;
                this.g.arc( this.xy, this.xy, this.radius - this.lineWidth + 1 + this.lineWidth * 2 / 3, 0, 2 * Math.PI, false);
                this.g.stroke();

                return false;
            }
        }
    });

    //carousel slider initialization
    $('.owl-carousel').owlCarousel({
        animateOut: 'slideOutLeft',
        animateIn: 'slideInRight',
        items:1,
        margin:30,
        stagePadding:30,
        smartSpeed:450,
        autoplay: true,
        loop: true
    });

    //summernote
    $('#summernote').summernote({
        placeholder: 'Item Description Goes Here...',
        tabsize: 2,
        height: 200
    });

    //Dropzone
    $("#ur-dropzone").dropzone({
        url: "#" // url to upload files to
    });

    //typed js initialization

    var element = $(".title-style2 span");
    element.typed({
        strings: ["找到你想要的.", "买下你需要的.", "转手你不需要的.", "加入我们，你会发现新大陆."],
        typeSpeed: 100,
        loop: true,
        smartBackspace: true
    });

});

// page loader

$(window).on('load', function(){
    $('.page-loader').fadeOut('slow',function(){
        $(this).remove();
    });
});
