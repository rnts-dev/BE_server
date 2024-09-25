package com.bside.backendapi.domain.member.application;

import com.bside.backendapi.domain.member.domain.Member;
import com.bside.backendapi.domain.member.dto.MemberDetailResponse;
import com.bside.backendapi.domain.member.dto.SearchIdResponse;
import com.bside.backendapi.domain.member.dto.SignUpResponse;
import com.bside.backendapi.domain.member.exception.MemberErrorCode;
import com.bside.backendapi.domain.member.exception.MemberException;
import com.bside.backendapi.domain.member.repository.MemberRepository;
import com.bside.backendapi.domain.member.vo.LoginId;
import com.bside.backendapi.domain.member.vo.Mail;
import com.bside.backendapi.domain.member.vo.Nickname;
import com.bside.backendapi.global.mail.exception.MailErrorCode;
import com.bside.backendapi.global.mail.exception.MailException;
import com.bside.backendapi.global.oauth2.domain.CustomOAuth2User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public SignUpResponse signUp(final Member member) {
        existedMail(member.getMail());
        existedNickname(member.getNickname());

        Member newMember = memberRepository.save(member.encode(passwordEncoder));
        return SignUpResponse.of(newMember);
    }

    public void existedLoginId(final LoginId loginId) {
        if (memberRepository.existsByLoginId(loginId))
            throw new MemberException(MemberErrorCode.DUPLICATED_LOGINID);
    }

    public void existedMail(final Mail mail) {
        if (memberRepository.existsByMail(mail))
            throw new MemberException(MemberErrorCode.DUPLICATED_MAIL);
    }

    private void existedNickname(final Nickname nickname) {
        if (memberRepository.existsByNickname(nickname))
            throw new MemberException(MemberErrorCode.DUPLICATED_NICKNAME);
    }

    public MemberDetailResponse getDetailByLoginId(final CustomOAuth2User principal) {
        return memberRepository.findByLoginId(LoginId.from(principal.getUsername()))
                .map(MemberDetailResponse::of)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));
    }

    public void update(final Member updateMember, final CustomOAuth2User principal, final Boolean isTendencyUpdate) {
        Member member = memberRepository.findByLoginId(LoginId.from(principal.getUsername()))
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        if (isTendencyUpdate) member.updateTendency(updateMember);
        else member.update(updateMember);
    }

    public void delete(final CustomOAuth2User principal) {
        memberRepository.delete(principal.getMember());
    }

    public void mailNotFound(final Mail mail) {
        if (!memberRepository.existsByMail(mail))
            throw new MailException(MailErrorCode.MAIL_NOT_FOUND);
    }

    public SearchIdResponse searchId(final boolean isVerified, String mail) {
        if (!isVerified)
            throw new MailException(MailErrorCode.VERIFICATION_FAILED);

        Member member = memberRepository.findByMail(Mail.from(mail))
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        return SearchIdResponse.of(member.getLoginId());
    }
}
